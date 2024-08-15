package com.luceroraul.pasionariastore.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.luceroraul.pasionariastore.interceptor.ErrorInterceptor
import com.luceroraul.pasionariastore.model.MenuItem
import com.luceroraul.pasionariastore.navigation.MyScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
open class SharedViewModel @Inject constructor(
    private val errorInterceptor: ErrorInterceptor,
    @ApplicationContext private val context: Context
) : ViewModel() {
    val loginErrorFlow = MutableSharedFlow<Pair<Int, String>>()
    val navigationFlow = MutableSharedFlow<String>()

    var isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
        private set

    val menuItems: List<MenuItem> = listOf(
        MenuItem(
            name = "Pedidos",
            imageVector = Icons.Default.ShoppingCart,
            onNavigatePath = MyScreens.CartList.name
        ), MenuItem(
            name = "Clientes",
            imageVector = Icons.Default.Person,
            onNavigatePath = MyScreens.CartList.name,
            enable = false
        ),
        MenuItem(
            name = "Ajustes",
            imageVector = Icons.Default.Settings,
            onNavigatePath = MyScreens.Setting.name,
            enable = true
        )
    )


    init {
        viewModelScope.launch {
            errorInterceptor.errorFlow.collectLatest {
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

    fun updateIsLoading(value: Boolean) {
         isLoading.update { value }
    }
}