package com.example.pasionariastore.model.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.pasionariastore.model.Cart
import com.example.pasionariastore.model.CartWithData
import com.example.pasionariastore.model.ProductCartWithData
import com.example.pasionariastore.model.ProductWithUnit
import kotlinx.coroutines.flow.MutableSharedFlow

data class CartUIState(
    val canSearchProducts: Boolean = false,
    val currentSearch: String = "",
    val showModalProductSearch: Boolean = false,
    val currentProductSearcheds: List<ProductWithUnit> = mutableListOf(),
    val currentProductCart: ProductCartWithData? = null,
    val lastSearch: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>(),
    val cartWithData: MutableState<CartWithData> = mutableStateOf(
        CartWithData(
            cart = Cart(),
            productCartWithData = mutableStateListOf()
        )
    )
) {

}
