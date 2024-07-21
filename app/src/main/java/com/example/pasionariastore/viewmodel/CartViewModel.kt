package com.example.pasionariastore.viewmodel

import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.pasionariastore.MyScreens
import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.model.CartUIState
import com.example.pasionariastore.model.Product
import com.example.pasionariastore.model.ProductCart
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CartUIState())
    val uiState: StateFlow<CartUIState> = _uiState.asStateFlow()

    var productCartList: List<ProductCart> = mutableListOf()
    var currentProductSearcheds: List<Product> = mutableListOf()
    var currentProductCart: ProductCart? = null


    /**
     * Indica si es posible utilizar el buscador y restablece valores
     */
    fun initProductScreen(navController: NavHostController, canSearchProducts: Boolean) {
        navController.navigate(MyScreens.CartProduct.name)
        _uiState.update { currentStates ->
            currentStates.copy(
                canSearchProducts = canSearchProducts,
                currentSearch = "",
                showModalProductSearch = false
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

    fun selectProductSearched(productSearched: Product) {
        currentProductCart = ProductCart(product = productSearched)
        // dejo de mostrar el modal
        _uiState.update { it.copy(showModalProductSearch = false) }
    }

    fun searchProducts() {
        // Filtro los productos en base parametro de busqueda
        currentProductSearcheds = Datasource.apiProducts.filter { String.format("%s %s", it.name, it.description).contains(uiState.value.currentSearch) }
        // Si existen valores para mostrar sigo, caso contrario tengo que emitir el mensaje

        // Abre el modal
        _uiState.update {
            it.copy(
                showModalProductSearch = true
            )
        }

    }

}