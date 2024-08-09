package com.example.pasionariastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

open class CustomDataStore(private val context: Context) {
    companion object {
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore("myData")
        val USER_DARK_MODE = booleanPreferencesKey("user_dark_mode")
        val TOKEN = stringPreferencesKey("token")
    }

    val getDarkMode: Flow<Boolean> = context.datastore.data.map {
        it[USER_DARK_MODE] ?: false
    }

    suspend fun saveDarkMode(value: Boolean) {
        context.datastore.edit {
            it[USER_DARK_MODE] = value
        }
    }

    suspend fun cleanToken() {
        context.datastore.edit {
            it.remove(TOKEN)
        }
    }

    fun getToken(): Flow<String> = context.datastore.data.map {
        it[TOKEN] ?: "eyJhbGciOiJIUzI1NiJ9.eyJ1c3VhcmlvSWQiOiIxIiwicm9sIjp7ImlkIjoxLCJjb2RpZ28iOiJBTEwiLCJkZXNjcmlwY2lvbiI6IlRvZG9zIGxvcyBwZXJtaXNvcyIsImF1dGhvcml0eSI6IkFMTCJ9LCJzdWIiOiJob21pdG93ZW4iLCJpYXQiOjE3MjI5MDcxMzEsImV4cCI6MTcyMjkzNTkzMX0.w5pOftynTjQpAz7bIOMknbmhJmHkwrTIgn8KOUOyjZo"
    }

    suspend fun saveToken(value: String) {
        context.datastore.edit {
            it[TOKEN] = value
        }
    }
}