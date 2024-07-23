package com.example.pasionariastore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.pasionariastore.MyScreens
import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.model.CartUIState
import com.example.pasionariastore.model.Product
import com.example.pasionariastore.model.ProductCart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class CartViewModel : ViewModel() {
    private val _state = MutableStateFlow(CartUIState())
    val state = _state

    /**
     * Indica si es posible utilizar el buscador y restablece valores
     */
    fun initProductScreen(navController: NavHostController, canSearchProducts: Boolean) {
        navController.navigate(MyScreens.CartProduct.name)
        state.update {
            it.copy(
                canSearchProducts = canSearchProducts,
                currentSearch = "",
                showModalProductSearch = false,
                currentProductCart = null
            )
        }
    }

    /**
     * Actualiza el valor del buscador al momento de tipear
     */
    fun updateCurrentSearch(newValue: String) {
        state.update {
            it.copy(
                currentSearch = newValue
            )
        }
    }

    fun selectProductSearched(productSearched: Product) {
        state.update {
            it.copy(
                currentProductCart = ProductCart(product = productSearched),
                showModalProductSearch = false
            )
        }
    }

    fun searchProducts() {
        state.update {
            it.copy(
                currentProductSearcheds = Datasource.apiProducts.filter { res ->
                    String.format("%s %s", res.name, res.description)
                        .contains(it.currentSearch)
                },
                showModalProductSearch = true
            )
        }
    }

    fun cancelProductSearch() {
        state.update {
            it.copy(showModalProductSearch = false)
        }
    }

    fun updateCurrentQuantity(newQuantity: String) {
        state.update {
            it.copy(
                currentProductCart = it.currentProductCart!!.copy(
                    amount = it.currentProductCart!!.amount.copy(quantity = newQuantity)
                )
            )
        }
    }

    fun calculatePrice(): String {
        var result = "0.0"
        var quantity = 0.0
        var price = 0.0
        state.value.currentProductCart?.let {
            quantity = it.amount.quantity.toDoubleOrNull() ?: 0.0
            price = it.product.priceList
            result = (price * quantity / 1000).toString()
        }
        return "ARS $result"
    }

    fun canAddProductToCart(): Boolean {
        var result = false
        if (state.value.currentProductCart != null)
            result =
                (state.value.currentProductCart!!.amount.quantity.toDoubleOrNull() ?: 0.0) > 0.0
        return result
    }

    fun addProductToCart(navController: NavHostController) {
        state.value.productCartList.add(state.value.currentProductCart!!)
        navController.navigate(MyScreens.Cart.name)
    }

    fun removeProductFromCart(product: ProductCart) {
        state.value.productCartList.remove(product)
    }

}