package com.example.pasionariastore.model.state

import androidx.annotation.ColorRes
import com.example.pasionariastore.R
import com.example.pasionariastore.model.Cart

data class CartListUIState(
    val stateFilters: List<CartStatus> = listOf(
        CartStatus.INACTIVE, CartStatus.PENDING, CartStatus.FINALIZED
    ),
    val carts: List<Cart> = emptyList()
)

enum class CartStatus(
    val label: String,
    val enabled: Boolean = true,
    @ColorRes val backgroundColor: Int
) {
    INACTIVE(
        label = "Inactivo",
        enabled = true,
        backgroundColor = R.color.delete
    ),
    PENDING(
        label = "Pendiente",
        enabled = true,
        backgroundColor = R.color.update
    ),
    FINALIZED(
        label = "Finalizado",
        enabled = true,
        backgroundColor = R.color.finalized
    ),
}
