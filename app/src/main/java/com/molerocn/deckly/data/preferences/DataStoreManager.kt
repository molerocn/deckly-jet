package com.molerocn.deckly.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

// Esta propiedad debe estar fuera de la clase, a nivel de archivo
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val NAME_KEY = stringPreferencesKey("user_name")
        private val EMAIL_KEY = stringPreferencesKey("user_email")
    }

    suspend fun saveUserData(token: String, name: String, email: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[NAME_KEY] = name
            prefs[EMAIL_KEY] = email
        }
    }

    suspend fun clearUserData() {
        context.dataStore.edit { it.clear() }
    }

    suspend fun getToken(): String {
        val prefs = context.dataStore.data.first()
        return prefs[TOKEN_KEY] ?: ""
    }

    suspend fun getUserName(): String {
        val prefs = context.dataStore.data.first()
        return prefs[NAME_KEY] ?: ""
    }

    suspend fun getUserEmail(): String {
        val prefs = context.dataStore.data.first()
        return prefs[EMAIL_KEY] ?: ""
    }
}
