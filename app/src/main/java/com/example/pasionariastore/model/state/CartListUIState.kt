package com.example.pasionariastore.model.state

import androidx.annotation.ColorRes
import androidx.compose.runtime.mutableStateListOf
import com.example.pasionariastore.R
import com.example.pasionariastore.model.CartWithData

data class CartListUIState(
    val stateFilters: MutableList<CartStatus> = mutableStateListOf(
        CartStatus.INACTIVE, CartStatus.PENDING, CartStatus.FINALIZED
    ),
    val cartsWithData: MutableList<CartWithData> = mutableStateListOf()
)

enum class CartStatus(
    val label: String,
    var enabled: Boolean = true,
    @ColorRes val backgroundColorActive: Int,
    @ColorRes val backgroundColorInactive: Int
) {
    INACTIVE(
        label = "Inactivo",
        enabled = true,
        backgroundColorActive = R.color.delete_active,
        backgroundColorInactive = R.color.delete_inactive
    ),
    PENDING(
        label = "Pendiente",
        enabled = true,
        backgroundColorActive = R.color.update_active,
        backgroundColorInactive = R.color.update_inactive
    ),
    FINALIZED(
        label = "Finalizado",
        enabled = true,
        backgroundColorActive = R.color.finalized_active,
        backgroundColorInactive = R.color.finalized_inactive
    ),
}
