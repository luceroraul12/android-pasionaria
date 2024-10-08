package com.luceroraul.pasionariastore.model.state

import androidx.annotation.ColorRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.luceroraul.pasionariastore.R
import com.luceroraul.pasionariastore.model.CartWithData

data class CartListUIState(
    val stateFilters: MutableList<CartStatus> = CartStatus.entries.toMutableList(),
    val cartsWithData: MutableList<CartWithData> = mutableStateListOf(),
    val hasCartsToSynchronized: MutableState<Boolean> = mutableStateOf(false)
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
    SYNCHRONIZED(
        label = "Sincronizado",
        enabled = true,
        backgroundColorActive = R.color.syncronized_active,
        backgroundColorInactive = R.color.syncronized_inactive,
    ),
}
