package com.luceroraul.pasionariastore.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luceroraul.pasionariastore.model.CartWithData
import com.luceroraul.pasionariastore.model.state.CartStatus
import com.luceroraul.pasionariastore.model.state.ResumeUIState
import com.luceroraul.pasionariastore.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
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

    fun initScreen() {
        val date = LocalDate.now()
        val monthName = date.month.getDisplayName(TextStyle.FULL, Locale("es", "ES"))
        val label = "$monthName - ${date.year}".uppercase()

        viewModelScope.launch {
            val carts = withContext(Dispatchers.IO) {
                cartRepository.getCartsWithStatus(
                    listOf(
                        CartStatus.FINALIZED.name,
                        CartStatus.PENDING.name,
                        CartStatus.SYNCHRONIZED.name
                    )
                ).first()
            }
            val topProducts = withContext(Dispatchers.IO) {
                cartRepository.getTopProducts().first()
            }

            state.update {
                ResumeUIState(
                    label = label, cartsWithData = carts.toMutableList(), topProducts = topProducts.toMutableList()
                )
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