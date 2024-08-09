package com.example.pasionariastore.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasionariastore.data.CustomDataStore
import com.example.pasionariastore.data.api.ApiBackend
import com.example.pasionariastore.model.BackendLogin
import com.example.pasionariastore.model.state.LoginUIState
import com.example.pasionariastore.navigation.MyScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiBackend: ApiBackend,
    private val customDataStore: CustomDataStore
) : ViewModel() {
    var state = MutableStateFlow(LoginUIState())
        private set

    fun cleanState(){
        // Dejo el mismo usuario por las dudas
        state.update { LoginUIState(username = state.value.username) }
    }

    fun updateUsername(value: String) {
        state.update { it.copy(username = value, enableLoginButton = checkEnableLoginButton()) }
    }

    fun updatePassword(value: String) {
        state.update { it.copy(password = value, enableLoginButton = checkEnableLoginButton()) }
    }

    fun login(context: Context, navigationFlow: MutableSharedFlow<String>) {
        var result = false
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                state.value.run {
                    val response = login(
                        username = username,
                        password = password,
                        context = context
                    )
                    if (response) {
                        navigationFlow.emit(MyScreens.Resume.name)
                    }
                }
            }
        }
    }

    suspend fun login(username: String, password: String, context: Context): Boolean {
        val response = apiBackend.login(BackendLogin(username = username, password = password))
        val result = response.isSuccessful
        if (result) {
            // Guardo el token
            customDataStore.saveToken(response.body()?.jwt ?: "VOID")
        }
        return result
    }

    private fun checkEnableLoginButton(): Boolean {
        state.value.run { return username.isNotEmpty() && password.isNotEmpty() }
    }

}