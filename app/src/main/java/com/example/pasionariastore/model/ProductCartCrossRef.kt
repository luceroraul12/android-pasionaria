package com.example.pasionariastore.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(primaryKeys = ["product_cart_id", "product_id"], indices = [Index("product_id")])
data class ProductCartCrossRef(
    @ColumnInfo(name = "product_cart_id")
    val productCartId: Long,
    @ColumnInfo(name = "product_id")
    val productId: Long
)