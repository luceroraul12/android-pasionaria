package com.example.pasionariastore.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDestination
import com.example.pasionariastore.data.CustomDataStore
import com.example.pasionariastore.data.api.ApiBackend
import com.example.pasionariastore.interceptor.BackendInterceptor
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
    private val customDataStore: CustomDataStore,
    private val interceptor: BackendInterceptor,
    @ApplicationContext private val context: Context

) : ViewModel() {
    var state = MutableStateFlow(LoginUIState())
        private set

    val loginErrorFlow = MutableSharedFlow<Pair<Int, String>>()
    val navigationFlow = MutableSharedFlow<String>()

    init {
        viewModelScope.launch {
            interceptor.errorFlow.collect {
                loginErrorFlow.emit(it)
            }
        }
    }

    fun updateUsername(value: String) {
        state.update { it.copy(username = value, enableLoginButton = checkEnableLoginButton()) }
    }

    fun updatePassword(value: String) {
        state.update { it.copy(password = value, enableLoginButton = checkEnableLoginButton()) }
    }

    fun login(context: Context) {
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

    fun resolveException(code: Int, message: String, currentDestination: NavDestination?) {
        viewModelScope.launch {
            var toastMessage = message
            withContext(Dispatchers.IO) {
                val isOnLoginScreen = currentDestination?.route.equals(MyScreens.Login.name)
                when (code) {
                    401 -> {
                       toastMessage = "Credenciales incorrectas"
                        if (!isOnLoginScreen)
                            navigationFlow.emit(MyScreens.Login.name)
                    }
                }
            }
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun checkEnableLoginButton(): Boolean {
        state.value.run { return username.isNotEmpty() && password.isNotEmpty() }
    }

}