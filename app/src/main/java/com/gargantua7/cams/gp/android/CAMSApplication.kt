package com.gargantua7.cams.gp.android

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import java.lang.ref.WeakReference

/**
 * @author Gargantua7
 */
class CAMSApplication : Application() {

    companion object {
        lateinit var _context: WeakReference<Context> private set
        val context by lazy { _context.get()!! }
        var username = ""
        var session = MutableLiveData("")
    }

    override fun onCreate() {
        super.onCreate()
        _context = WeakReference(applicationContext)
    }
}
