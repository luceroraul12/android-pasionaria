package com.example.pasionariastore.model.state

import androidx.compose.runtime.mutableStateListOf
import com.example.pasionariastore.model.Cart
import com.example.pasionariastore.model.CartWithData
import kotlinx.coroutines.flow.MutableStateFlow

data class CartUIState(
    val cartWithData: MutableStateFlow<CartWithData> = MutableStateFlow(
        CartWithData(
            cart = Cart(),
            productCartWithData = mutableStateListOf()
        )
    ),
    val canFinalize: Boolean = false
)