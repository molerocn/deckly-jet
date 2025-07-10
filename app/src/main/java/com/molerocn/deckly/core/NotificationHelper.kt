package com.molerocn.deckly.core

import android.app.NotificationChannel
import android.app.NotificationManager
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import android.os.Build
import jakarta.inject.Inject
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import kotlin.random.Random
import com.molerocn.deckly.R

class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun sendNotification(title: String, message: String) {
        val notificationManager = ContextCompat.getSystemService(context, NotificationManager::class.java)
        val notificationId = Random.nextInt()

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // notificationManager?.notify(notificationId, builder.build())
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Default Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Canal para notificaciones generales"
            }

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "default_channel"
    }
}

