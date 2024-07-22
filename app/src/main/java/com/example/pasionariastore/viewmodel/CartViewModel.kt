package com.example.pasionariastore.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.pasionariastore.MyScreens
import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.model.CartUIState
import com.example.pasionariastore.model.Product
import com.example.pasionariastore.model.ProductCart

class CartViewModel : ViewModel() {
    var state by mutableStateOf(CartUIState())
        private set

    /**
     * Indica si es posible utilizar el buscador y restablece valores
     */
    fun initProductScreen(navController: NavHostController, canSearchProducts: Boolean) {
        navController.navigate(MyScreens.CartProduct.name)
        state = state.copy(
            canSearchProducts = canSearchProducts,
            currentSearch = "",
            showModalProductSearch = false
        )
    }

    /**
     * Actualiza el valor del buscador al momento de tipear
     */
    fun updateCurrentSearch(newValue: String) {
        state = state.copy(currentSearch = newValue)
    }

    fun selectProductSearched(productSearched: Product) {
        state = state.copy(
            currentProductCart = ProductCart(product = productSearched),
            showModalProductSearch = false
        )
    }

    fun searchProducts() {
        state = state.copy(
            currentProductSearcheds = Datasource.apiProducts.filter { res ->
                String.format("%s %s", res.name, res.description)
                    .contains(state.currentSearch)
            },
            showModalProductSearch = true
        )
    }

    fun cancelProductSearch() {
        state = state.copy(showModalProductSearch = false)
    }

    fun updateCurrentQuantity(newValue: String) {
        state = state.copy(
            currentAmount = state.currentAmount.copy(quantity = newValue.toDouble())
        )
    }

    fun calculatePrice(): String {
        return state.currentAmount.quantity.toString()
    }

}