package com.luceroraul.pasionariastore.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luceroraul.pasionariastore.model.ProductCart
import com.luceroraul.pasionariastore.model.ProductWithUnit
import com.luceroraul.pasionariastore.model.state.CartProductUIState
import com.luceroraul.pasionariastore.repository.CartRepository
import com.luceroraul.pasionariastore.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.Currency
import javax.inject.Inject

@HiltViewModel
open class CartProductViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    var state = MutableStateFlow(CartProductUIState())
        private set

    private val _operationCompleted = MutableStateFlow(false)
    val operationCompleted = _operationCompleted.asStateFlow()

    private var jobs: MutableList<Job> = mutableListOf()

    fun emitFocus(value: Boolean, delayTime: Long = 500): Unit {
        val job =
        viewModelScope.launch {
            delay(delayTime)
            state.value.lastSearch.emit(value = value)
        }
        jobs.add(job)
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
        jobs.forEach{it.cancel()}
        jobs = mutableListOf()
        updateState(CartProductUIState())
    }

    private fun updateState(newState: CartProductUIState) {
        state.update { newState }
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
        val job = viewModelScope.launch(Dispatchers.IO) {
            if (productCartId > 0) {
                cartRepository.getCartProductWithDataById(productCartId)
                    .collect { productCartWithData ->
                        if (productCartWithData != null)
                            updateState(
                                state.value.copy(
                                    currentProductCart = productCartWithData.productCart.copy(cartId = cartId),
                                    currentProductWithUnit = productCartWithData.productWithUnit,
                                    canSearchProducts = false,
                                    canUpdateQuantity = true,
                                    initCartId = cartId,
                                    isNew = false
                                )
                            )
                    }
            } else {
                updateState(
                    state.value.copy(
                        currentProductCart = ProductCart(cartId = cartId),
                        canSearchProducts = true,
                        canUpdateQuantity = false,
                        initCartId = cartId,
                        isNew = true
                    )
                )
            }
            emitFocus(true, 0)
        }
        jobs.add(job)
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
            val job = viewModelScope.launch(Dispatchers.IO) {
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
                                )
                            )
                        }

                    }
            }
            jobs.add(job)
        }
    }

    fun selectProductSearched(productWithUnit: ProductWithUnit): Unit {
        // Finalizo el job de busqueda
        jobs.forEach{it.cancel()}
        // Actualizo el productCart que se esta generando y seteo lo que se tiene que mostrar y dejo de mostrar el modal
        updateState(
            state.value.copy(
                currentProductCart = state.value.currentProductCart.copy(
                    productId = productWithUnit.product.productId,
                ),
                currentProductWithUnit = productWithUnit,
                productsFound = emptyList(),
                canUpdateQuantity = true
            )
        )
        emitFocus(true)
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
        val job = viewModelScope.launch(Dispatchers.IO) {
            state.value.let {
                var message = "El producto fue agregado al pedido"
                if (it.isNew) {
                    cartRepository.insertProductCart(it.currentProductCart)
                } else {
                    message = "El producto fue actualizado"
                    cartRepository.updateProductCart(it.currentProductCart)
                }
                // Asegúrate de que la operación de inserción se complete antes de continuar
                withContext(Dispatchers.Main) {
                    showMessage(context = context, message = message)
                    emitFocus(false, 0)
                    cleanState()
                    _operationCompleted.value = true
                }
            }
        }
        jobs.add(job)
    }

    fun cancelCurrentSearch() {
        state.update {
            it.copy(
                currentSearch = "",
                productsFound = emptyList()
            )
        }
    }

}