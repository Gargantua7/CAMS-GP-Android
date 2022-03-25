package com.gargantua7.cams.gp.android.logic.dao

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/**
 * @author Gargantua7
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore("dao")
