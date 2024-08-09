package com.example.pasionariastore.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.example.pasionariastore.interceptor.BackendInterceptor
import com.example.pasionariastore.navigation.MyScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val interceptor: BackendInterceptor,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val loginErrorFlow = MutableSharedFlow<Pair<Int, String>>()
    val navigationFlow = MutableSharedFlow<String>()

    init {
        viewModelScope.launch {
            interceptor.errorFlow.collect {
                loginErrorFlow.emit(it)
            }
        }
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

    fun initSubscriptionScreens(coroutineScope: CoroutineScope, navController: NavController) {
        coroutineScope.launch {
            launch {
                navigationFlow.collect {
                    navController.navigate(it)
                }
            }
            launch {
                loginErrorFlow.collect {
                    resolveException(
                        code = it.first,
                        message = it.second,
                        currentDestination = navController.currentDestination,
                    )
                }
            }
        }
    }
}