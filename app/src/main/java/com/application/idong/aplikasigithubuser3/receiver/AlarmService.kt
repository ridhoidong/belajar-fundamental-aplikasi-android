package com.application.idong.aplikasigithubuser3.receiver

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.application.idong.aplikasigithubuser3.R
import com.application.idong.aplikasigithubuser3.ui.SplashscreenActivity
import com.application.idong.aplikasigithubuser3.utils.ViewUtil

class AlarmService : Service() {

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val title = intent.getStringExtra(AlarmReceiver.EXTRA_TITLE)
        val message = intent.getStringExtra(AlarmReceiver.EXTRA_MESSAGE)
        val openIntent = Intent(this, SplashscreenActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, AlarmReceiver.ID_ALARM, openIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        ViewUtil.showNotification(this, R.drawable.ic_schedule, title, message, AlarmReceiver.CHANNEL_ID, AlarmReceiver.CHANNEL_NAME, AlarmReceiver.ID_ALARM, pendingIntent)

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented");
    }
}