package com.luceroraul.pasionariastore.model.state

import androidx.compose.runtime.mutableStateListOf
import com.luceroraul.pasionariastore.model.Cart
import com.luceroraul.pasionariastore.model.CartWithData
import kotlinx.coroutines.flow.MutableStateFlow

data class CartUIState(
    val cartWithData: MutableStateFlow<CartWithData> = MutableStateFlow(
        CartWithData(
            cart = Cart(),
            productCartWithData = mutableStateListOf()
        )
    ),
    val hasProducts: Boolean = false
)