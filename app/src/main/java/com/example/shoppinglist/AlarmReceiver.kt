package com.example.shoppinglist

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {

            val i = Intent(context, MyLists::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            val pendingIntent =
                PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE)

            val builder = NotificationCompat.Builder(context!!, "Channel1")
                .setSmallIcon(R.drawable.baseline_shopping_cart_24)
                .setContentTitle("Reminder")
                .setContentText("Don't forget to buy the items")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)

            val notificationManager = NotificationManagerCompat.from(context)

            notificationManager.notify(200, builder.build())

    }
}