package com.gargantua7.cams.gp.android.ui.message

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.R
import com.gargantua7.cams.gp.android.logic.model.Message
import com.gargantua7.cams.gp.android.logic.repository.MsgRepository
import com.gargantua7.cams.gp.android.logic.repository.PersonRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class MessagePollingService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        Log.d("PollingService", "onCreate")
        val manager: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel("msg", "Message", NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("PollingService", "onStartCommand")
        if (CAMSApplication.session.value == null) return super.onStartCommand(intent, flags, startId)
        MainScope().launch {
            val res = MsgRepository.getMsg()
            try {
                res.getOrThrow().forEach {
                    Log.d("PollingService", "msg: $it")
                    if (it.sender != it.recipient) {
                        MsgRepository.saveMsgIntoDB(it)
                    }
                    val p = PersonRepository.getPersonByUsername(it.sender)
                    val person = p.getOrThrow()
                    showNotification(it, person.name)
                }
            } catch (_: Throwable) {
                Toast.makeText(this@MessagePollingService, "Network Error", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    //初始化通知栏配置
    private fun showNotification(message: Message, sender: String) {
//        val intent = Intent(this, Main2Activity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val channelId = "msg"
        val notification: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(sender)
            .setContentText(message.content)
            //.setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(100, notification.build())
    }
}
