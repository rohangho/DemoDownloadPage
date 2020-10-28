package com.demosample.demophonexdownload.broadcast

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.demosample.demophonexdownload.R
import com.demosample.demophonexdownload.activity.MainActivity.Companion.wv
import com.demosample.demophonexdownload.utility.JsInterface

class NotificationListner : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        if (JsInterface.playing) {
            wv.evaluateJavascript("mediaElement.pause();", null)
            createNotificationChannel(context)
            val expandedView = RemoteViews(context.packageName, R.layout.notification_ui)

            expandedView.setImageViewResource(R.id.icon, R.drawable.ic_launcher_foreground)
            expandedView.setTextViewText(R.id.title, "Browser")
            expandedView.setTextViewText(R.id.desc, "This is a description. - PLAYING")
            expandedView.setImageViewResource(R.id.pausePlay, R.drawable.ic_baseline_play_arrow_24)
            val builder = NotificationCompat.Builder(context, "0")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Demo Heading")
                .setContentText("Demo Content")
                .setCustomBigContentView(expandedView)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)


            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, builder.build())
            val pendingSwitchIntent = PendingIntent.getBroadcast(
                context, 0, Intent(
                    context,
                    NotificationListner::class.java
                ), 0
            )
            expandedView.setPendingIntentTemplate(R.id.pausePlay, pendingSwitchIntent)
        } else {
            wv.evaluateJavascript("mediaElement.pause();", null)
            createNotificationChannel(context)
            val expandedView = RemoteViews(context.packageName, R.layout.notification_ui)

            expandedView.setImageViewResource(R.id.icon, R.drawable.ic_launcher_foreground)
            expandedView.setTextViewText(R.id.title, "Browser")
            expandedView.setTextViewText(R.id.desc, "This is a description. - PLAYING")
            expandedView.setImageViewResource(R.id.pausePlay, R.drawable.ic_baseline_play_arrow_24)
            val builder = NotificationCompat.Builder(context, "0")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Demo Heading")
                .setContentText("Demo Content")
                .setCustomBigContentView(expandedView)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)


            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, builder.build())

            val pendingSwitchIntent = PendingIntent.getBroadcast(
                context, 0, Intent(
                    context,
                    NotificationListner::class.java
                ), 0
            )
            expandedView.setPendingIntentTemplate(R.id.pausePlay, pendingSwitchIntent)
        }

    }

    private fun createNotificationChannel(mcontext: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                "0",
                "Demo",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager: NotificationManager =
                mcontext.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}