package com.example.pasionariastore.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasionariastore.data.api.ApiBackend
import com.example.pasionariastore.model.BackendErrorResponse
import com.example.pasionariastore.model.BackendLogin
import com.example.pasionariastore.model.state.LoginUIState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
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
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                state.value.run {
                    val response =
                        apiBackend.login(BackendLogin(username = username, password = password))
                    var data: ResponseBody? = response.errorBody()
                    Log.i("login", "prueba object ${response.toString()}")
                    Log.i("login", "body success ${response.body()}")

                    val errorBodyString = response.errorBody()?.string() ?: ""

                    // Crear una instancia de Gson
                    val gson = Gson()

                    // Convertir la cadena JSON a un objeto de tu clase de error
                    val errorResponse =
                        gson.fromJson(errorBodyString, BackendErrorResponse::class.java)

                    // Ahora puedes acceder a los campos de errorResponse
                    Log.i("login", errorResponse.message)
                }
            }
        }
    }

}