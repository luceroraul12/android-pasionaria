package com.example.pasionariastore.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.pasionariastore.model.CartWithData
import com.example.pasionariastore.navigation.MyScreens
import com.example.pasionariastore.model.ProductCartWithData
import com.example.pasionariastore.model.state.CartUIState
import com.example.pasionariastore.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
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
                    it.copy(cartWithData = MutableStateFlow(cart), canFinalize = hasPriceFromProducts(cart))
                }
            }
        }
    }

    fun removeProductFromCart(data: ProductCartWithData, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteProductCart(productCart = data.productCart)
            showMessage(context = context, message = "El producto fue removido del pedido")
        }
    }

    fun goToProductCartScreen(
        navController: NavController,
        cartId: Long,
        productCartId: Long
    ) {
        navController.navigate("${MyScreens.CartProduct.name}/${cartId}?productCartId=${productCartId}")
    }

    fun showMessage(message: String, context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun formatPriceNumber(value: Double): String {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.setMaximumFractionDigits(2)
        format.currency = Currency.getInstance("ARS")

        return format.format(value)
    }

    fun hasPriceFromProducts(cart: CartWithData): Boolean {
        return cart.productCartWithData.any{p -> p.productCart.totalPrice > 0.0}
    }
}