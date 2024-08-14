package com.example.pasionariastore.model.state

import androidx.compose.runtime.mutableStateListOf
import com.example.pasionariastore.model.CartWithData
import com.example.pasionariastore.model.Product

data class ResumeUIState (
    val label: String = "VACIO",
    val cartsWithData: MutableList<CartWithData> = mutableStateListOf(),
    val topProducts: MutableList<Product> = mutableStateListOf()
)
