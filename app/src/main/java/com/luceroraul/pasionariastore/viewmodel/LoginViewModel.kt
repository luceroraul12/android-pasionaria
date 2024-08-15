package com.luceroraul.pasionariastore.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luceroraul.pasionariastore.data.CustomDataStore
import com.luceroraul.pasionariastore.model.BackendLogin
import com.luceroraul.pasionariastore.model.state.LoginUIState
import com.luceroraul.pasionariastore.navigation.MyScreens
import com.luceroraul.pasionariastore.repository.BackendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
open class LoginViewModel @Inject constructor(
    private val backendRepository: BackendRepository,
    private val customDataStore: CustomDataStore
) : ViewModel() {
    var state = MutableStateFlow(LoginUIState())
        private set

    fun cleanState() {
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
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                state.value.run {
                    val response = login(
                        username = username,
                        password = password,
                    )
                    if (response) {
                        navigationFlow.emit(MyScreens.Resume.name)
                    }
                }
            }
        }
    }

    suspend fun login(username: String, password: String): Boolean {
        val response =
            backendRepository.login(BackendLogin(username = username, password = password))
        return if (response != null) {
            // Guardo el token
            customDataStore.saveToken(response.jwt)
            true
        } else false
    }

    private fun checkEnableLoginButton(): Boolean {
        state.value.run { return username.isNotEmpty() && password.isNotEmpty() }
    }

}