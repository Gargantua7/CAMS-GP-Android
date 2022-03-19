package com.gargantua7.cams.android

import android.app.Application
import android.content.Context
import java.lang.ref.WeakReference

/**
 * @author Gargantua7
 */
class CAMSApplication : Application() {

    companion object {
        private lateinit var _context: WeakReference<Context>
        val context by lazy { _context.get()!! }
    }

    override fun onCreate() {
        super.onCreate()
        _context = WeakReference(applicationContext)
    }
}
