package com.luceroraul.pasionariastore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import com.luceroraul.pasionariastore.data.CustomDataStore
import com.luceroraul.pasionariastore.navigation.NavManager
import com.luceroraul.pasionariastore.ui.theme.PasionariaStoreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val dataStore = CustomDataStore(this)
            PasionariaStoreTheme(
                darkTheme = dataStore.getDarkMode.collectAsState(initial = false).value
            ) {
                NavManager(dataStore = dataStore)
            }
        }
    }
}