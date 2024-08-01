package com.example.pasionariastore.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pasionariastore.MyScreens
import com.example.pasionariastore.model.ProductCartWithData
import com.example.pasionariastore.model.state.CartUIState
import com.example.pasionariastore.repository.CartRepository
import com.example.pasionariastore.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Currency
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val checkDatabaseViewModel: CheckDatabaseViewModel
) : ViewModel() {
    var state = MutableStateFlow(CartUIState())
        private set

    fun cleanState() {
        state.update {
            CartUIState()
        }
    }

    fun initScreenByCart(cartId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            cartRepository.getCartWithData(cartId).collect { cart ->
                if (cart != null) state.update {
                    it.copy(cartWithData = mutableStateOf(cart))
                }
            }
        }
    }

    /**
     * Indica si es posible utilizar el buscador y restablece valores
     */

    fun goToAddNewProductCartScreen(goToNewProductCart: (Long) -> Unit) {
        goToNewProductCart(state.value.cartWithData.value.cart.id)
        viewModelScope.launch {
            delay(1000)
            state.value.lastSearch.emit(value = Unit)
        }
    }

    fun cancelProductSearch() {
        state.update {
            it.copy(showModalProductSearch = false)
        }
    }

    fun removeProductFromCart(data: ProductCartWithData, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteProductCart(productCart = data.productCart)
            showMessage(context = context, message = "El producto fue removido del pedido")

        }
    }

    fun goToUpdateProductCart(
        product: ProductCartWithData, goToCartProductScreen: (Long, Long) -> Unit
    ) {
        state.update {
            it.copy(
                currentProductCart = product,
                canSearchProducts = false,
            )
        }
        state.value.currentProductCart.productCart.let {
            goToCartProductScreen(it.cartId, it.productCartId)
        }
        viewModelScope.launch {
            delay(1000)
            state.value.lastSearch.emit(Unit)
        }
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
}