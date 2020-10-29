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
import com.demosample.demophonexdownload.activity.MainActivity.Companion.img
import com.demosample.demophonexdownload.activity.MainActivity.Companion.webTitle
import com.demosample.demophonexdownload.broadcast.NotificationListner
import java.lang.Boolean


class JsInterface(val mContext: Context) {
    @JavascriptInterface
    fun mediaAction(type: String?) {
        Log.e("Info", type!!)
        playing = Boolean.parseBoolean(type)
        if (playing) {
            createNotificationChannel()

            val expandedView = RemoteViews(mContext.packageName, R.layout.notification_ui_expanded)

            val normalView = RemoteViews(mContext.packageName, R.layout.notification_ui)
            normalView.setImageViewBitmap(R.id.icon_normal, img)
            normalView.setTextViewText(R.id.title_normal, "MagTapp")

            expandedView.setImageViewBitmap(R.id.icon, img)
            expandedView.setTextViewText(R.id.title, "MagTapp")
            expandedView.setTextViewText(R.id.desc, webTitle)
            expandedView.setImageViewResource(R.id.pausePlay, R.drawable.ic_baseline_pause_24)

            val pendingSwitchIntent = PendingIntent.getBroadcast(
                    mContext, 0, Intent(
                    mContext,
                    NotificationListner::class.java
            ), 0
            )
            expandedView.setOnClickPendingIntent(R.id.pausePlay, pendingSwitchIntent)

            val builder = NotificationCompat.Builder(mContext, "0")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setCustomContentView(expandedView)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)


            val notificationManager =
                mContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, builder.build())


        } else {
            createNotificationChannel()

            val expandedView = RemoteViews(mContext.packageName, R.layout.notification_ui_expanded)
            val normalView = RemoteViews(mContext.packageName, R.layout.notification_ui)
            normalView.setImageViewBitmap(R.id.icon_normal, img)
            normalView.setTextViewText(R.id.title_normal, "MagTapp")

            expandedView.setImageViewBitmap(R.id.icon, img)
            expandedView.setTextViewText(R.id.title, "MagTapp")
            expandedView.setTextViewText(R.id.desc, webTitle)
            expandedView.setImageViewResource(R.id.pausePlay, R.drawable.ic_baseline_play_arrow_24)

            val pendingSwitchIntent = PendingIntent.getBroadcast(
                    mContext, 0, Intent(
                    mContext,
                    NotificationListner::class.java
            ), 0
            )
            expandedView.setOnClickPendingIntent(R.id.pausePlay, pendingSwitchIntent)

            val builder = NotificationCompat.Builder(mContext, "0")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setCustomContentView(expandedView)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)


            val notificationManager =
                mContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, builder.build())


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
                NotificationManager.IMPORTANCE_LOW
            )
            val manager: NotificationManager =
                mContext.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }


}