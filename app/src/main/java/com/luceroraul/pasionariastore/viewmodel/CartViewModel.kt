package com.luceroraul.pasionariastore.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.luceroraul.pasionariastore.model.CartWithData
import com.luceroraul.pasionariastore.model.ProductCartWithData
import com.luceroraul.pasionariastore.model.state.CartStatus
import com.luceroraul.pasionariastore.model.state.CartUIState
import com.luceroraul.pasionariastore.navigation.MyScreens
import com.luceroraul.pasionariastore.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.Currency
import javax.inject.Inject

@HiltViewModel
open class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    @ApplicationContext val context: Context,
) : ViewModel() {
    var state = MutableStateFlow(CartUIState())
        private set

    fun initScreenByCart(cartId: Long) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                cartRepository.getCartWithData(cartId)
            }

            result.collect { cart ->
                if (cart != null) state.update {
                    it.copy(
                        cartWithData = MutableStateFlow(cart),
                        hasProducts = hasPriceFromProducts(cart)
                    )
                }
            }
        }
    }

    fun removeProductFromCart(data: ProductCartWithData) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteProductCart(productCart = data.productCart)
            withContext(Dispatchers.Main){
                showMessage(message = "El producto fue removido del pedido")
            }
        }
    }

    fun goToProductCartScreen(
        navController: NavController,
        cartId: Long,
        productCartId: Long
    ) {
        navController.navigate("${MyScreens.CartProduct.name}/${cartId}?productCartId=${productCartId}")
    }

    fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun formatPriceNumber(value: Double): String {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.setMaximumFractionDigits(2)
        format.currency = Currency.getInstance("ARS")

        return format.format(value)
    }

    fun hasPriceFromProducts(cart: CartWithData): Boolean {
        return cart.productCartWithData.any { p -> p.productCart.totalPrice > 0.0 }
    }

    fun finalizeCart(navController: NavController, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            // Actualizo el estado a FINALIZEd
            val cart = state.value.cartWithData.value.cart.copy(status = CartStatus.FINALIZED.name)
            cartRepository.updateCart(cart)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Pedido finalizado", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        }
    }
}