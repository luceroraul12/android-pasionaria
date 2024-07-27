package com.example.pasionariastore.model.state

import androidx.compose.runtime.mutableStateListOf
import com.example.pasionariastore.model.ProductCartWithData
import com.example.pasionariastore.model.ProductWithUnit
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.Unit

data class CartUIState(
    val canSearchProducts: Boolean = false,
    val currentSearch: String = "",
    val showModalProductSearch: Boolean = false,
    val productCartList: MutableList<ProductCartWithData> = mutableStateListOf(),
    val currentProductSearcheds: List<ProductWithUnit> = mutableListOf(),
    val currentProductCart: ProductCartWithData? = null,
    val lastSearch: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
) {

}
