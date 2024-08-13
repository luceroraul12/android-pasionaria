package com.example.pasionariastore.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasionariastore.data.CustomDataStore
import kotlinx.coroutines.launch
import javax.inject.Inject

open class SettingViewModel @Inject constructor(
    private val dataStore: CustomDataStore
) : ViewModel() {

    var darkMode = mutableStateOf(false)
        private set

    fun updateDarkMode(value: Boolean) {
        darkMode.value = value
        viewModelScope.launch {
            dataStore.saveDarkMode(darkMode.value)
        }
    }
}