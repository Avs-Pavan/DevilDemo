package com.kevin.jevil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kevin.devil.models.DevilMessage
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import timber.log.Timber
import java.lang.reflect.Type

abstract class BaseActivity : AppCompatActivity() {
    val gson: Gson = Gson()
    val app: App = App()
    lateinit var mqttAndroidClient: MqttAndroidClient
    val serverUri = "tcp://134.209.144.25:1883"
    var clientId = "daredevil"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        app.userMessages = fetchPreviousMessages()
        initDevl()
    }

    private fun initDevl() {
        clientId += System.currentTimeMillis()
        mqttAndroidClient = MqttAndroidClient(applicationContext, serverUri, clientId)
        mqttAndroidClient.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(reconnect: Boolean, serverURI: String) {
                if (reconnect) {
                    log("Reconnected to : $serverURI")
                    // Because Clean Session is true, we need to re-subscribe
                    subscribeToTopic()
                } else {
                    // publishMessage();
                    log("Connected to: $serverURI")

                }
            }

            override fun connectionLost(cause: Throwable) {
                log("The Connection was lost.")
            }

            override fun messageArrived(topic: String, message: MqttMessage) {
                log("Incoming message: " + String(message.payload))
            }

            override fun deliveryComplete(token: IMqttDeliveryToken) {}
        })
        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.isAutomaticReconnect = true
        mqttConnectOptions.isCleanSession = false
        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    val disconnectedBufferOptions = DisconnectedBufferOptions()
                    disconnectedBufferOptions.isBufferEnabled = true
                    disconnectedBufferOptions.bufferSize = 100
                    disconnectedBufferOptions.isPersistBuffer = false
                    disconnectedBufferOptions.isDeleteOldestMessages = false
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions)
                    subscribeToTopic()
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    log("Failed to connect to: $serverUri")
                }
            })
        } catch (ex: MqttException) {
            ex.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        val locStr = gson.toJson(app.userMessages)
        val prefs = this.getSharedPreferences("list", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("data", locStr)
        editor.apply()
        Timber.e("onpause -- "+prefs.getString("data",""))
    }


    fun log(message: String) {
        Log.e("Mqtt client ", message)
    }

    private fun subscribeToTopic() {
        try {
            mqttAndroidClient.subscribe(
                "M/+", 1
            ) { topic: String, message: MqttMessage ->
                // message Arrived!
                runOnUiThread {
                    app.userMessages.add(gson.fromJson(String(message.payload), DevilMessage::class.java))
                    updateMessage(topic, String(message.payload))
                }
            }
        } catch (ex: Exception) {

        } catch (ex: MqttException) {
            System.err.println("Exception whilst subscribing")
            ex.printStackTrace()
        }
    }
    private fun fetchPreviousMessages(): ArrayList<DevilMessage> {
        return try {
            val prefs = this.getSharedPreferences("list", MODE_PRIVATE)
            val json = prefs.getString("data", "")
            val type: Type = object : TypeToken<ArrayList<DevilMessage>>() {}.type
            gson.fromJson(json, type)
        } catch (e:java.lang.Exception){
            arrayListOf()
        }
    }
    override fun onResume() {
        super.onResume()
        val prefs = this.getSharedPreferences("list", MODE_PRIVATE)
        Timber.e(prefs.getString("data",""))
    }


    abstract fun updateMessage(topic: String, payload: String)

}