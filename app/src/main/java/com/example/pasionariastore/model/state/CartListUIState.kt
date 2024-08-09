package com.example.pasionariastore.model.state

import androidx.annotation.ColorRes
import androidx.compose.runtime.mutableStateListOf
import com.example.pasionariastore.R
import com.example.pasionariastore.model.CartWithData

data class CartListUIState(
    val stateFilters: MutableList<CartStatus> = CartStatus.entries.toMutableList(),
    val cartsWithData: MutableList<CartWithData> = mutableStateListOf()
)

enum class CartStatus(
    val label: String,
    var enabled: Boolean = true,
    val canDeleteCart: Boolean = false,
    val canDeleteProducts: Boolean = false,
    val canEditProducts: Boolean = false,
    @ColorRes val backgroundColorActive: Int,
    @ColorRes val backgroundColorInactive: Int
) {
    PENDING(
        label = "Pendiente",
        enabled = true,
        backgroundColorActive = R.color.update_active,
        backgroundColorInactive = R.color.update_inactive,
        canDeleteCart = true,
        canDeleteProducts = true,
        canEditProducts = true
    ),
    FINALIZED(
        label = "Finalizado",
        enabled = true,
        backgroundColorActive = R.color.finalized_active,
        backgroundColorInactive = R.color.finalized_inactive,
    ),
    SYNCRONIZED(
        label = "Sincronizado",
        enabled = true,
        backgroundColorActive = R.color.syncronized_active,
        backgroundColorInactive = R.color.syncronized_inactive,
    ),
}
