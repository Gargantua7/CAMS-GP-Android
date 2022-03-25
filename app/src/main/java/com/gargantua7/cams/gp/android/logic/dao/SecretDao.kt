package com.gargantua7.cams.gp.android.logic.dao

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.gargantua7.cams.gp.android.CAMSApplication
import kotlinx.coroutines.flow.first

/**
 * @author Gargantua7
 */
object SecretDao {

    private val SESSION = stringPreferencesKey("session")

    suspend fun saveSession(session: String) {
        CAMSApplication.context.dataStore.edit { map ->
            map[SESSION] = session
        }
    }

    suspend fun loadSession(): String? {
        return CAMSApplication.context.dataStore.data.first()[SESSION]
    }

    suspend fun removeSession() {
        CAMSApplication.context.dataStore.edit {
            it.remove(SESSION)
        }
    }
}
