package com.example.pasionariastore.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pasionariastore.model.state.LoginUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    var state = MutableStateFlow(LoginUIState())
        private set

    fun updateUsername(value: String) {
        state.update { it.copy(username = value) }
    }

    fun updatePassword(value: String) {
        state.update { it.copy(password = value) }
    }

}