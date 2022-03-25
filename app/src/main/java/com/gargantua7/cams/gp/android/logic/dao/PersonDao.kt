package com.gargantua7.cams.gp.android.logic.dao

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.gargantua7.cams.gp.android.CAMSApplication
import kotlinx.coroutines.flow.first

/**
 * @author Gargantua7
 */
object PersonDao {

    private val USERNAME = stringPreferencesKey("username")

    suspend fun saveUsername(username: String) {
        CAMSApplication.context.dataStore.edit { map ->
            map[USERNAME] = username
        }
    }

    suspend fun loadUsername(): String? {
        return CAMSApplication.context.dataStore.data.first()[USERNAME]
    }

    suspend fun removeUsername() {
        CAMSApplication.context.dataStore.edit {
            it.remove(USERNAME)
        }
    }

}
