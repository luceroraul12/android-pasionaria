package com.luceroraul.pasionariastore.model.state

import androidx.compose.runtime.mutableStateListOf
import com.luceroraul.pasionariastore.model.CartWithData
import com.luceroraul.pasionariastore.model.Product

data class ResumeUIState (
    val label: String = "VACIO",
    val cartsWithData: MutableList<CartWithData> = mutableStateListOf(),
    val topProducts: MutableList<Product> = mutableStateListOf()
)
