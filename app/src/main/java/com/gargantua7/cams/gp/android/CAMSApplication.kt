package com.gargantua7.cams.gp.android

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.gargantua7.cams.gp.android.logic.exception.AuthorizedException
import com.gargantua7.cams.gp.android.logic.model.Person
import com.gargantua7.cams.gp.android.logic.repository.PersonRepository
import com.gargantua7.cams.gp.android.logic.repository.SecretRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.net.UnknownHostException

/**
 * @author Gargantua7
 */
class CAMSApplication : Application() {

    companion object {
        lateinit var _context: WeakReference<Context> private set
        val context by lazy { _context.get()!! }
        var username: String? = null
        var session = MutableLiveData<String?>()
        var errorMsg by mutableStateOf<String?>(null)
        var loading by mutableStateOf(false)
        var user = Transformations.switchMap<String?, Person?>(session) {
            liveData<Person?>(Dispatchers.IO) {
                if (it == null) emit(null)
                else {
                    loading = true
                    val result = PersonRepository.getMyInfo()
                    if (result.isSuccess) {
                        val list = result.getOrThrow()
                        if (list.size == 1) {
                            emit(list.single())
                        } else {
                            errorMsg = "Not Found"
                        }
                    } else {
                        errorMsg = when (result.exceptionOrNull()) {
                            is AuthorizedException -> "Invalid Session"
                            is UnknownHostException -> "Network Error"
                            else -> "Unknown Server Exception"
                        }
                        Log.w("Get Me", result.exceptionOrNull())
                    }
                    loading = false
                    delay(1000)
                    errorMsg = null
                }
            }
        }
    }

    private val scope = MainScope()

    override fun onCreate() {
        super.onCreate()
        _context = WeakReference(applicationContext)
        scope.launch {
            loading = true
            PersonRepository.loadUsername()
            SecretRepository.loadSession()
            loading = false
        }
    }
}
