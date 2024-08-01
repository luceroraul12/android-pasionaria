package com.example.pasionariastore.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.ProductWithUnit
import com.example.pasionariastore.model.state.CartProductUIState
import com.example.pasionariastore.repository.CartRepository
import com.example.pasionariastore.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.Currency
import javax.inject.Inject

@HiltViewModel
class CartProductViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    var state = MutableStateFlow(CartProductUIState())
        private set

    fun emitFocus(): Unit {
        viewModelScope.launch {
            delay(1000)
            state.value.lastSearch.emit(value = Unit)
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

    fun cleanState(): Unit {
        updateState(CartProductUIState())
    }

    private fun updateState(newState: CartProductUIState) {
        state.update {
            newState
        }
    }

    fun updateCurrentQuantity(newValue: String) {
        updateState(
            state.value.copy(
                currentProductCart = state.value.currentProductCart.copy(
                    quantity = newValue
                )
            )
        )
        calculatePriceProductCart()
    }

    fun initScreen(cartId: Long, productCartId: Long): Unit {
        // Si cuenta con ProductCartId, significa que previamente se habia guardado el producto y no necesita volver a buscar productos
        // Busco el producto
        viewModelScope.launch(Dispatchers.IO) {
            state.update {
                state.value.copy(initCartId = cartId, isNew = mutableStateOf(productCartId == 0L))
            }
            if (productCartId > 0) {
                cartRepository.getCartProductWithDataById(productCartId)
                    .collect { productCartWithData ->
                        updateState(
                            state.value.copy(
                                // Seteo las propiedades del producto
                                currentProductCart = productCartWithData.productCart.copy(cartId = cartId),
                                currentProductWithUnit = productCartWithData.productWithUnit,
                                // Al existir, no tiene que dejar buscar productos
                                canSearchProducts = false,
                                canUpdateQuantity = true,
                                showModalProductSearch = false
                            )
                        )
                    }
            } else {
                updateState(
                    state.value.copy(
                        // Seteo las propiedades del producto
                        currentProductCart = ProductCart(cartId = cartId),
                        canSearchProducts = true,
                        canUpdateQuantity = false,
                        showModalProductSearch = false
                    )
                )
            }
            emitFocus()
        }
    }

    fun updateCurrentSearch(newValue: String): Unit {
        updateState(
            state.value.copy(currentSearch = newValue)
        )
    }

    fun searchProducts(context: Context): Unit {
        if (state.value.currentSearch.isEmpty()) {
            Toast.makeText(context, "Debe escribir algo para buscar", Toast.LENGTH_SHORT).show()
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                productRepository.getProductsWithUnitBySearch(
                    search = state.value.currentSearch,
                    cartId = state.value.initCartId
                )
                    .collect { products ->
                        if (products.isEmpty()) {
                            showMessage(context = context, message = "No hay coincidencias")
                        } else {
                            // Seteo los productos encontrados y muestro el modal
                            updateState(
                                state.value.copy(
                                    productsFound = products,
                                    showModalProductSearch = true
                                )
                            )
                        }
                    }
            }
        }
    }

    fun selectProductSearched(productWithUnit: ProductWithUnit): Unit {
        // Actualizo el productCart que se esta generando y seteo lo que se tiene que mostrar y dejo de mostrar el modal
        updateState(
            state.value.copy(
                currentProductCart = state.value.currentProductCart.copy(
                    productId = productWithUnit.product.productId,
                ),
                currentProductWithUnit = productWithUnit,
                showModalProductSearch = false,
                canUpdateQuantity = true
            )
        )
        emitFocus()
    }

    fun setShowModal(show: Boolean) {
        updateState(state.value.copy(showModalProductSearch = show))
    }

    fun calculatePriceProductCart(): String {
        var result = 0.0
        var quantity = ""
        var price = 0.0
        state.value.let { item ->
            quantity = item.currentProductCart.quantity
            price = item.currentProductWithUnit.product.priceList
            result = (price * (quantity.toDoubleOrNull() ?: 0.0)) / 1000
            val currentProductCart = state.value.currentProductCart.copy(
                quantity = quantity.toString(),
                totalPrice = result,
            )
            updateState(
                state.value.copy(
                    currentProductCart = currentProductCart
                )
            )
        }
        return formatPriceNumber(result)
    }

    fun createOrUpdateProductCart(context: Context): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            state.value.let {
                var message = "El producto fue agregado al pedido"
                if (it.isNew.value){
                    cartRepository.insertProductCart(it.currentProductCart)
                } else {
                    message = "El producto fue actualizado"
                    cartRepository.updateProductCart(it.currentProductCart)
                }
                showMessage(context = context, message = message)
            }
        }
    }
}