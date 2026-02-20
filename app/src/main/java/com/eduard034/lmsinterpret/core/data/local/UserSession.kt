package com.eduard034.lmsinterpret.core.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

@Singleton
class UserSession @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_TOKEN = stringPreferencesKey("user_token")
    }

    suspend fun saveSession(name: String, token: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_NAME] = name
            prefs[USER_TOKEN] = token
        }
    }

    val userName: Flow<String?> = context.dataStore.data.map { prefs -> prefs[USER_NAME] }

    val userToken: Flow<String?> = context.dataStore.data.map { prefs -> prefs[USER_TOKEN] }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}