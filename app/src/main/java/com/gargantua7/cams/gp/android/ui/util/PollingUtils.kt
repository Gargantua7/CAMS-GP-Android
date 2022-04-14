package com.gargantua7.cams.gp.android.ui.util

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log


/**
 * @author Gargantua7
 */
@SuppressLint("UnspecifiedImmutableFlag")
fun startPollingService(context: Context, seconds: Int, cls: Class<*>?, action: String?) {
    Log.d("PollingUtils", "startPollingService")
    //获取AlarmManager系统服务
    val manager = context
        .getSystemService(Context.ALARM_SERVICE) as AlarmManager

    //包装需要执行Service的Intent
    val intent = Intent(context, cls)
    intent.action = action
    val pendingIntent = PendingIntent.getService(
        context, 0,
        intent, PendingIntent.FLAG_UPDATE_CURRENT
    )

    //使用AlarmManger的setRepeating方法设置定期执行的时间间隔（seconds秒）和需要执行的Service
    manager.setRepeating(
        AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),
        (seconds * 100).toLong(), pendingIntent
    )
}

//停止轮询服务
@SuppressLint("UnspecifiedImmutableFlag")
fun stopPollingService(context: Context, cls: Class<*>?, action: String?) {
    val manager = context
        .getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, cls)
    intent.action = action
    val pendingIntent = PendingIntent.getService(
        context, 0,
        intent, PendingIntent.FLAG_UPDATE_CURRENT
    )
    //取消正在执行的服务
    manager.cancel(pendingIntent)
}


