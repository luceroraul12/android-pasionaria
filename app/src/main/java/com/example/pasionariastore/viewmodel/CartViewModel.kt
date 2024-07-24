package com.example.pasionariastore.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.pasionariastore.MyScreens
import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.model.CartUIState
import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.ProductWithUnit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(

): ViewModel() {
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

    fun selectProductSearched(productSearched: ProductWithUnit) {
        state.update {
            it.copy(
                currentProductCart = ProductCart(productWithUnit = productSearched),
                showModalProductSearch = false
            )
        }
    }

    fun searchProducts(context: Context) {
        if (state.value.currentSearch.isNullOrEmpty()) {
            Toast.makeText(context, "Debe escribir algo para buscar", Toast.LENGTH_SHORT).show()
        } else {
            val productFiltered = Datasource.apiProducts.filter { res ->
                String.format("%s %s", res.product.name, res.product.description)
                    .contains(state.value.currentSearch)
            }
            if (productFiltered.isNullOrEmpty()) {
                Toast.makeText(context, "No existen coincidencias", Toast.LENGTH_SHORT).show()
            } else {
                state.update {
                    it.copy(
                        currentProductSearcheds = productFiltered,
                        showModalProductSearch = true
                    )
                }
            }
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
                    quantity = newQuantity
                )
            )
        }
    }

    fun calculateCartPrice(): String = (state.value.productCartList.map { p -> p.totalPrice }
        .reduceOrNull { acc, price -> acc + price } ?: 0.0).toString()

    fun calculatePrice(): String {
        var result = 0.0
        var quantity = 0.0
        var price = 0.0
        state.value.currentProductCart?.let { productCart ->
            quantity = productCart.quantity.toDoubleOrNull() ?: 0.0
            price = productCart.productWithUnit.product.priceList
            result = (price * quantity / 1000)
            state.update {
                it.copy(
                    currentProductCart = productCart.copy(totalPrice = result)
                )
            }
        }
        return "ARS $result"
    }

    fun canAddProductToCart(): Boolean {
        var result = false
        if (state.value.currentProductCart != null)
            result =
                (state.value.currentProductCart!!.quantity.toDoubleOrNull() ?: 0.0) > 0.0
        return result
    }

    fun addProductToCart(navController: NavHostController, context: Context) {
        // Si el product ya se encuentra en el pedido, tengo que actualizarlo
        val index: Int =
            state.value.productCartList.indexOfFirst { it.productWithUnit == state.value.currentProductCart?.productWithUnit }
        var message = "El producto fue agregado al pedido"
        if (index >= 0) {
            state.value.productCartList.set(index, state.value.currentProductCart!!)
            message = "El producto fue actualizado"
        } else {
            state.value.productCartList.add(state.value.currentProductCart!!)
        }
        navController.navigate(MyScreens.Cart.name)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun removeProductFromCart(product: ProductCart, context: Context) {
        state.value.productCartList.remove(product)
        Toast.makeText(context, "El producto fue removido del pedido", Toast.LENGTH_SHORT).show()
    }

    fun updateProductCart(product: ProductCart, navController: NavController, context: Context) {
        state.update {
            it.copy(
                currentProductCart = product
            )
        }
        navController.navigate(MyScreens.CartProduct.name)
    }

}