package jdr.tv.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

fun Context.sendNotification(id: Int, contentText: String) {
    val channelId = "jdr.tv.notification.sync"
    val channelName = "Show Sync"
    val channelDescriptionText = "Show Sync Service"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel = NotificationChannel(channelId, channelName, importance).apply {
        description = channelDescriptionText
    }
    (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)

    val contentTitle = "Show Sync"
    val notification = NotificationCompat.Builder(this, channelId)
        .setContentTitle(contentTitle)
        .setContentText(contentText)
        .setShowWhen(true)
        .build()

    NotificationManagerCompat.from(this).notify(id, notification)
}
