package com.luceroraul.pasionariastore.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luceroraul.pasionariastore.data.CustomDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class SettingViewModel @Inject constructor(
    private val dataStore: CustomDataStore
) : ViewModel() {

    var darkMode = mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            dataStore.getDarkMode.collect {
                darkMode.value = it
            }
        }
    }

    fun updateDarkMode(value: Boolean) {
        darkMode.value = value
        viewModelScope.launch {
            dataStore.saveDarkMode(darkMode.value)
        }
    }

    fun updateLoading(value: Boolean) {
        darkMode.value = value
        viewModelScope.launch {
            dataStore.saveDarkMode(darkMode.value)
        }
    }
}