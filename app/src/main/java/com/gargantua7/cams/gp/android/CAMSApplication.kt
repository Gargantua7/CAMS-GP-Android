package com.gargantua7.cams.gp.android

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import androidx.room.Room
import com.gargantua7.cams.gp.android.logic.dao.MsgDatabase
import com.gargantua7.cams.gp.android.logic.exception.AuthorizedException
import com.gargantua7.cams.gp.android.logic.exception.NotFoundException
import com.gargantua7.cams.gp.android.logic.model.Person
import com.gargantua7.cams.gp.android.logic.repository.PersonRepository
import com.gargantua7.cams.gp.android.logic.repository.SecretRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.net.UnknownHostException

/**
 * @author Gargantua7
 */
class CAMSApplication : Application() {

    companion object {
        private lateinit var _context: WeakReference<Context>
        val context by lazy { _context.get()!! }
        var username: String? = null
        var session = MutableLiveData<String?>()
        var loading by mutableStateOf(false)
        var user = Transformations.switchMap<String?, Person?>(session) {
            liveData<Person?>(Dispatchers.Main) {
                var errorMsg: String? = null
                if (it == null) emit(null)
                else {
                    loading = true
                    val result = PersonRepository.getMyInfo()
                    if (result.isSuccess) {
                        emit(result.getOrThrow())
                    } else {
                        errorMsg = when (result.exceptionOrNull()) {
                            is AuthorizedException -> "Login Expired"
                            is NotFoundException -> "Not Found"
                            is UnknownHostException -> "Network Error"
                            else -> "Unknown Server Exception"
                        }
                        Log.w("Get Me", result.exceptionOrNull())
                    }
                    if (result.exceptionOrNull() is AuthorizedException) {
                        session.value = null
                        username = null
                        PersonRepository.removeUsername()
                        SecretRepository.removeSession()
                        emit(null)
                    }
                    loading = false
                    errorMsg?.let { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
                }
            }
        }
        val msgDB = Transformations.switchMap(session) {
            liveData(Dispatchers.IO) {
                if (it == null) emit(null)
                else emit(
                    Room.databaseBuilder(
                        context,
                        MsgDatabase::class.java, it
                    ).allowMainThreadQueries().build()
                )
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
