package com.example.pasionariastore.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
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
import kotlinx.coroutines.flow.collect
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
                    login(
                        username = username,
                        password = password,
                        context = context
                    )
                }
            }
        }
    }

    suspend fun login(username: String, password: String, context: Context) {
        val response = apiBackend.login(BackendLogin(username = username, password = password))
        if (response.isSuccessful) {
            // Guardo el token
            customDataStore.saveToken(response.body()?.jwt ?: "VOID")
        }
    }

    fun resolveException(code: Int, message: String, navController: NavHostController) {
        when (code) {
            401 -> {
                Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                navController.navigate(MyScreens.Login.name)
            }

            else -> {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkEnableLoginButton(): Boolean {
        state.value.run { return username.isNotEmpty() && password.isNotEmpty() }
    }

}