package com.neowise.backgroundservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.neowise.backgroundservice.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val broadcastReceiver = TimeBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            startService(Intent(this, BackgroundService::class.java))
        }

        registerReceiver(broadcastReceiver, IntentFilter(BackgroundService.ACTION))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    fun updateStatus(status: String) {
        binding.status.text = status
    }

    inner class TimeBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {

            Log.d("Broadcast", "receive broadcast")

            if (intent.action == BackgroundService.ACTION) {

                Log.d("Broadcast", "action")

                intent.getStringExtra(BackgroundService.STATUS)
                    ?.let(this@MainActivity::updateStatus)
            }
        }
    }
}