package com.example.pasionariastore.model.state

import androidx.compose.runtime.mutableStateListOf
import com.example.pasionariastore.model.CartWithData

data class ResumeUIState (
    val label: String = "VACIO",
    val cartsWithData: MutableList<CartWithData> = mutableStateListOf()
)
