package com.neowise.backgroundservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.*

class BackgroundService : Service() {

    companion object {
        const val ACTION = "com.neowise.backgroundservice.ACTION"
        const val STATUS = "com.neowise.backgroundservice.ACTION.STATUS"
    }

    private val timer = Timer()
    private var index = 0

    override fun onBind(intent: Intent?): IBinder? {
        return null;
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("LocalService", "Received start id $startId: $intent")

        timer.schedule(object : TimerTask() {
            override fun run() {
                index++
                sendBroadcast(Intent(ACTION).putExtra(STATUS, "status $index"))
            }
        }, 0, 1000)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}