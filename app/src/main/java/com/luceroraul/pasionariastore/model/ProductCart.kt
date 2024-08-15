package com.luceroraul.pasionariastore.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index(value = ["product_id", "cart_id"], unique = true)]
)
data class ProductCart(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_cart_id")
    val productCartId: Long = 0,
    @ColumnInfo(name = "backend_product_cart_id")
    val backendProductCartId: Long? = null,
    @ColumnInfo(name = "quantity")
    val quantity: String = "",
    @ColumnInfo(name = "totalPrice")
    val totalPrice: Double = 0.0,
    @ColumnInfo(name = "product_id")
    val productId: Long = 0,
    @ColumnInfo(name = "cart_id")
    val cartId: Long = 0,
)