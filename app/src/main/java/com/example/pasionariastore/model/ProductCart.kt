package com.example.pasionariastore.model

import androidx.room.Entity

@Entity
data class ProductCart(
    val productWithUnit: ProductWithUnit,
    val quantity: String = "",
    val totalPrice: Double = 0.0
)