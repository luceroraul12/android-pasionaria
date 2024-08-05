package com.example.pasionariastore.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasionariastore.model.CartWithData
import com.example.pasionariastore.model.MenuItem
import com.example.pasionariastore.model.state.CartStatus
import com.example.pasionariastore.model.state.ResumeUIState
import com.example.pasionariastore.navigation.MyScreens
import com.example.pasionariastore.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
open class ResumeViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    var state: MutableStateFlow<ResumeUIState> = MutableStateFlow(ResumeUIState())
        private set

    val menuItems: List<MenuItem> = listOf(
        MenuItem(
            name = "Pedidos",
            imageVector = Icons.Default.ShoppingCart,
            onNavigatePath = MyScreens.CartList.name
        ), MenuItem(
            name = "Clientes",
            imageVector = Icons.Default.Person,
            onNavigatePath = MyScreens.CartList.name,
            enable = false
        )
    )

    fun initScreen() {
        val date = LocalDate.now()
        val monthName = date.month.getDisplayName(TextStyle.FULL, Locale("es", "ES"))
        val label = "$monthName - ${date.year}".uppercase()
        var carts: List<CartWithData> = emptyList()

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                cartRepository.getCartsWithStatus(
                    listOf(
                        CartStatus.FINALIZED.name, CartStatus.PENDING.name
                    )
                ).collect {
                    carts = it
                    state.update {
                        ResumeUIState(
                            label = label, cartsWithData = carts.toMutableList()
                        )
                    }
                }
            }

        }


    }

    fun calculatePairResume(cartWithData: List<CartWithData>, status: String?): Pair<Int, Int> {
        var size = 0
        var price = 0
        val cartsFiltered =
            cartWithData.filter { if (status.isNullOrEmpty()) true else it.cart.status.equals(status) }
        if (cartsFiltered.isNotEmpty()) {
            size = cartsFiltered.size
            price = (cartsFiltered.map { it.productCartWithData }.flatten()
                .map { it.productCart.totalPrice }.reduceOrNull() { a, b -> (a + b) }
                ?: 0.0).toInt()
        }

        return Pair(size, price)
    }

}