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
            showModalProductSearch = false,
            currentProductCart = null
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

    fun updateCurrentQuantity(newQuantity: String) {
        state = state.copy(
            currentProductCart = state.currentProductCart!!.copy(
                amount = state.currentProductCart!!.amount.copy(quantity = newQuantity)
            )
        )
    }

    fun calculatePrice(): String {
        var result = "0.0"
        var quantity = 0.0
        var price = 0.0
        var unitValue = 0.0
        state.currentProductCart?.let {
            quantity = it.amount.quantity.toDoubleOrNull() ?: 0.0
            price = it.product.priceList
            unitValue = it.product.unit.value
            result = (quantity * price * unitValue).toString()
        }
        return "ARS $result"
    }

    fun canAddProductToCart(): Boolean {
        var result = false
        if (state.currentProductCart != null)
            result = (state.currentProductCart!!.amount.quantity.toDoubleOrNull() ?: 0.0) > 0.0
        return result
    }

}