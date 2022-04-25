package com.kevin.jevil

import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kevin.devil.Devil
import com.kevin.devil.models.DevilMessage
import kotlinx.android.synthetic.main.activity_messaging.*
import timber.log.Timber
import java.lang.reflect.Type


class MessagingActivity : BaseActivity() {
    lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)

        messageAdapter = MessageAdapter(this, listOf())
        app.userMessages = fetchPreviousMessages()
        messages.adapter = messageAdapter
        Timber.e(" size " + app.userMessages.size)
        messageAdapter.updateList(app.userMessages)
        try {
            messages.smoothScrollToPosition(messageAdapter.itemCount)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        sendMessage.setOnClickListener {
            if (messageEt.text.isNotEmpty()) {
                Devil.getHermes().sendMessage(
                    DevilMessage(
                        "user",
                        messageEt.text.toString(),
                        System.currentTimeMillis(),
                        0L,
                        false,
                        true
                    )
                )
                messageEt.setText("")
            } else
                messageEt.error = "Please enter message to send"
        }
    }

    override fun updateMessage(topic: String, payload: String) {
        messageAdapter.add(gson.fromJson(payload, DevilMessage::class.java))
        Timber.e(" size " + app.userMessages.size)
        try {
            messages.smoothScrollToPosition(messageAdapter.itemCount)
        } catch (e: Exception) {
            e.printStackTrace()
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
        app.userMessages = fetchPreviousMessages()
        Timber.e(" size " + app.userMessages.size)
        messageAdapter.updateList(app.userMessages)
    }

//    override fun onPause() {
//        super.onPause()
//        val locStr = gson.toJson(messageAdapter.list)
//        val prefs = this.getSharedPreferences("list", MODE_PRIVATE)
//        val editor = prefs.edit()
//        editor.putString("data", locStr)
//        editor.apply()
//
//    }

}