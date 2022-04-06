package com.kevin.jevil.devl;

import android.util.Log;

import com.kevin.jevil.BuildConfig;
import com.kevin.jevil.devl.models.DevilConfig;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONException;

import io.reactivex.rxjava3.annotations.NonNull;

public final class RemoteDevil {

    private static DevilConfig sConfig;
    private static String clientId = "ExampleAndroidClient";
    private static MqttAndroidClient sMqttAndroidClient;

    private static IMqttDeliveryToken deliveryToken;

    public static void breath(@NonNull DevilConfig devilConfig) {
        sConfig = devilConfig;
        DevilStore.build(sConfig);
        DevilStore.goBlank();
        try {
            connectToServer();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private static void connectToServer() throws MqttException {
        clientId = clientId + System.currentTimeMillis();
        sMqttAndroidClient = new MqttAndroidClient(sConfig.getmContext(), sConfig.getDevilsAddress(), clientId);

        // connection callback to listen to connection status
        sMqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
            }

            @Override
            public void connectionLost(Throwable cause) {
                //connectionStatus = false;
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                // received messages
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                if (deliveryToken == token) {
                    Log.e("Devil message status", "message delivered");
                }

            }
        });


        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);

        // will message to publish death of client
        mqttConnectOptions.setWill(sConfig.getWillTopic(), sConfig.getUserNameOrId().getBytes(), 1, false);
        // start connection
        sMqttAndroidClient.connect(mqttConnectOptions, sConfig.getmContext(), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                disconnectedBufferOptions.setBufferEnabled(true);
                disconnectedBufferOptions.setBufferSize(100);
                disconnectedBufferOptions.setPersistBuffer(false);
                disconnectedBufferOptions.setDeleteOldestMessages(false);
                sMqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                scream("Devil is alive", Devil.Topics.ALIVE);
                processMessageQueue();
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                // connection failure
                // exception.printStackTrace();
            }
        });
    }

    /**
     * Send messages to server and other listeners.
     */

    static void scream(String messageString, String topic) {

        if (BuildConfig.DEBUG)
            Log.e("Devil message", messageString + "  Topic " + topic);
        try {
            MqttMessage message = new MqttMessage();
            message.setRetained(false);
            message.setQos(1);
            message.setPayload(messageString.getBytes());
            if (sMqttAndroidClient.isConnected()) {
                deliveryToken = sMqttAndroidClient.publish(topic, message);
            } else {
                //store to db
                if (BuildConfig.DEBUG)
                    Log.e(sConfig.getTag(), "Devil is offline to store scream to DevilStore");
                DevilStore.storeScreams(messageString, topic);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processing queued messages
     **/

    private static void processMessageQueue() {
        JSONArray pendingArray = DevilStore.getPendingScreams();
        for (int x = 0; x < pendingArray.length(); x++) {
            try {
                String string = pendingArray.get(x).toString();
                String[] arr = string.split("----");
                scream(arr[0], arr[1]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    static void kill() {
        try {
            scream("Devil is Killed consciously", Devil.Topics.DEAD);
            DevilStore.goBlank();
            sMqttAndroidClient.close();
            sMqttAndroidClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
