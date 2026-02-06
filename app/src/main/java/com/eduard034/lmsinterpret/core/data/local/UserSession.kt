package com.eduard034.lmsinterpret.core.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserSession(private val context: Context) {
    companion object {
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_TOKEN = stringPreferencesKey("user_token")
    }

    // Guardar datos tras el login
    suspend fun saveSession(name: String, token: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_NAME] = name
            prefs[USER_TOKEN] = token
        }
    }

    // Obtener el nombre de usuario para el menú
    val userName: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_NAME]
    }

    // Agrega esto en tu UserSession
    val userToken: Flow<String?> = context.dataStore.data.map { prefs -> prefs[USER_TOKEN] }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}