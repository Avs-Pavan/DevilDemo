package com.kevin.jevil

import android.app.Application
import com.kevin.devil.Devil
import com.kevin.devil.models.DevilConfig
import com.kevin.devil.models.DevilMessage
import timber.log.Timber

class App : Application() {
    val serverUri = "tcp://134.209.144.25:1883"
    lateinit var userMessages:  ArrayList<DevilMessage>

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Devil.breath(
            DevilConfig(
                true,
                true,
                serverUri,
                applicationContext,
                "Tag",
                "Will_topic",
                "123456"
            )
        )
    }
}