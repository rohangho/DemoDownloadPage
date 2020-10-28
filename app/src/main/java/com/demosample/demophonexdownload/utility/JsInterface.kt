package com.demosample.demophonexdownload.utility

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.demosample.demophonexdownload.R
import com.demosample.demophonexdownload.broadcast.NotificationListner
import java.lang.Boolean


class JsInterface(val mContext: Context) {
    @JavascriptInterface
    fun mediaAction(type: String?) {
        Log.e("Info", type!!)
        playing = Boolean.parseBoolean(type)
        if (playing) {
//            val mNotificationManager =
//                mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            val builder = Notification.Builder(mContext)
//            val notification = builder.notification
//            notification.icon = R.drawable.ic_launcher_foreground
//            val contentView = RemoteViews(mContext.getPackageName(), R.layout.notification_ui)
//            notification.contentView = contentView
//            notification.flags = notification.flags or Notification.FLAG_ONGOING_EVENT
//            contentView.setTextViewText(R.id.title, "Browser")
//            contentView.setTextViewText(R.id.desc, "This is a description. - PLAYING")
//            contentView.setImageViewResource(R.id.pausePlay, R.drawable.ic_baseline_pause_24)
//            val pendingSwitchIntent = PendingIntent.getBroadcast(
//                mContext,
//                0,
//                Intent(mContext, MainActivity::class.java),
//                0
//            )
//            contentView.setOnClickPendingIntent(R.id.pausePlay, pendingSwitchIntent)
//            mNotificationManager.notify(99, notification)

            createNotificationChannel()

            val expandedView = RemoteViews(mContext.packageName, R.layout.notification_ui)

            expandedView.setImageViewResource(R.id.icon, R.drawable.ic_launcher_foreground)
            expandedView.setTextViewText(R.id.title, "Browser")
            expandedView.setTextViewText(R.id.desc, "This is a description. - PLAYING")
            expandedView.setImageViewResource(R.id.pausePlay, R.drawable.ic_baseline_pause_24)
            val builder = NotificationCompat.Builder(mContext, "0")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Demo Heading")
                .setContentText("Demo Content")
                .setCustomBigContentView(expandedView)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)


            val notificationManager =
                mContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, builder.build())

            val pendingSwitchIntent = PendingIntent.getBroadcast(
                mContext, 0, Intent(
                    mContext,
                    NotificationListner::class.java
                ), 0
            )
            expandedView.setPendingIntentTemplate(R.id.pausePlay, pendingSwitchIntent)

        } else {
            createNotificationChannel()

            val expandedView = RemoteViews(mContext.packageName, R.layout.notification_ui)

            expandedView.setImageViewResource(R.id.icon, R.drawable.ic_launcher_foreground)
            expandedView.setTextViewText(R.id.title, "Browser")
            expandedView.setTextViewText(R.id.desc, "This is a description. - PLAYING")
            expandedView.setImageViewResource(R.id.pausePlay, R.drawable.ic_baseline_play_arrow_24)
            val builder = NotificationCompat.Builder(mContext, "0")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Demo Heading")
                .setContentText("Demo Content")
                .setCustomBigContentView(expandedView)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)


            val notificationManager =
                mContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, builder.build())


            val pendingSwitchIntent = PendingIntent.getBroadcast(
                mContext, 0, Intent(
                    mContext,
                    NotificationListner::class.java
                ), 0
            )
            expandedView.setPendingIntentTemplate(R.id.pausePlay, pendingSwitchIntent)

        }
    }

    companion object {
        var playing = false
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                "0",
                "Demo",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager: NotificationManager =
                mContext.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }


}