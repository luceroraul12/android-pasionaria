package com.example.pasionariastore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.pasionariastore.MyScreens
import com.example.pasionariastore.model.CartUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CartUIState())
    val uiState: StateFlow<CartUIState> = _uiState.asStateFlow()


    /**
     * Indica si es posible utilizar el buscador y restablece valores
     */
    fun initProductScreen(navController: NavHostController, canSearchProducts: Boolean) {
        navController.navigate(MyScreens.CartProduct.name)
        _uiState.update { currentStates ->
            currentStates.copy(
                canSearchProducts = canSearchProducts,
                currentSearch = ""
            )
        }
    }

    /**
     * Actualiza el valor del buscador al momento de tipear
     */
    fun updateCurrentSearch(newValue: String) {
        _uiState.update {
            it.copy(currentSearch = newValue)
        }
    }

}