package com.example.pasionariastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CustomDataStore(private val context: Context) {
    companion object {
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore("myData")
        val USER_DARK_MODE = booleanPreferencesKey("user_dark_mode")
    }

    val getDarkMode: Flow<Boolean> = context.datastore.data.map {
        it[USER_DARK_MODE] ?: false
    }

    suspend fun saveDarkMode(value: Boolean) {
        context.datastore.edit {
            it[USER_DARK_MODE] = value
        }
    }
}