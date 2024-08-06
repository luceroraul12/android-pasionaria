package com.example.pasionariastore.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasionariastore.data.api.ApiBackend
import com.example.pasionariastore.model.BackendLogin
import com.example.pasionariastore.model.state.LoginUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiBackend: ApiBackend
) : ViewModel() {
    var state = MutableStateFlow(LoginUIState())
        private set

    fun updateUsername(value: String) {
        state.update { it.copy(username = value) }
    }

    fun updatePassword(value: String) {
        state.update { it.copy(password = value) }
    }

    fun login() {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                state.value.run {
//                    val response =
//                        apiBackend.login(BackendLogin(username = username, password = password))
//                    Log.i("login", response.toString())
//                }
//            }
//        }
    }

}