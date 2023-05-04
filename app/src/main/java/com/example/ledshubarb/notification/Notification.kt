package com.example.ledshubarb.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.ledshubarb.R
import com.example.ledshubarb.view.WindowActivity

const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"

class Notification : Service() {
    companion object {

    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val inputName = intent?.getStringExtra("name")
        val inputSinger = intent?.getStringExtra("singer")

        createNotificationChannel()
        val notificationIntent = Intent(this, WindowActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(this, channelID)
            .setSmallIcon(R.drawable.air380x380)
            .setContentTitle(inputName)
            .setContentText(inputSinger)
            .build()
        startForeground(notificationID,notification)

        return START_NOT_STICKY
    }
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notif Channel"
            val desc = "A Description of the Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, name, importance)
            channel.description = desc
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}