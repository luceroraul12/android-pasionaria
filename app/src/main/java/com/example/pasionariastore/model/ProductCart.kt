package com.example.pasionariastore.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductCart(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_cart_id")
    val productCartId: Long = 0,
    @ColumnInfo(name = "quantity")
    val quantity: String = "",
    @ColumnInfo(name = "totalPrice")
    val totalPrice: Double = 0.0,
    @ColumnInfo(name = "product_id")
    val productId: Long,
)