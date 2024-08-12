package com.example.pasionariastore.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasionariastore.model.Cart
import com.example.pasionariastore.model.state.CartListUIState
import com.example.pasionariastore.model.state.CartStatus
import com.example.pasionariastore.repository.CartRepository
import com.example.pasionariastore.usecase.CartSynchronize
import com.example.pasionariastore.usecase.ProductSynchronizer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
open class CartListViewModel @Inject constructor(
    protected val cartRepository: CartRepository,
    private val cartSynchronize: CartSynchronize

) : ViewModel() {
    var state = MutableStateFlow(CartListUIState())
        private set

    init {
        getCarts()
    }


    fun updateChipStatus(chip: CartStatus): Unit {
        val index: Int = state.value.stateFilters.indexOf(chip)
        val statues: MutableList<CartStatus> = state.value.stateFilters
        statues.set(index, chip.apply {
            enabled = !enabled
        })
        state.update {
            it.copy(
                stateFilters = statues.toMutableStateList()
            )
        }
        // Vuelvo a actualizar el listado de pedidos
        getCarts()
    }

    fun getCarts(): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            var status: List<String> = emptyList()
            // Al trabajar con la corutina en IO, tengo que esperar hasta que se genere el state en MAIN
            withContext(Dispatchers.Main){
                status = state.value.stateFilters.filter(CartStatus::enabled).map { it.name }
            }
            cartRepository.getCartsWithStatus(status).collect { carts ->
                state.update {
                    it.copy(
                        cartsWithData = carts.toMutableStateList(),
                        hasCartsToSynchronized = mutableStateOf(carts.any {c -> c.cart.status.equals(CartStatus.FINALIZED.name)})
                    )
                }
            }
        }
    }

    fun createNewCart(): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.insertCart(Cart())
        }
    }

    fun deleteCart(cart: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteCart(cart)
        }
    }

    fun trySynchronize() {
        viewModelScope.launch(Dispatchers.IO) {
            val carts = state.value.cartsWithData.filter { c -> c.cart.status.equals(CartStatus.FINALIZED.name) }
            cartSynchronize.trySynchronizeCarts(carts);
        }
    }
}