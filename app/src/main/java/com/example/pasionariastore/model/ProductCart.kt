package com.example.pasionariastore.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class ProductCart(
    @PrimaryKey(autoGenerate = true)
    val productCartId: Long = 0,
    @ColumnInfo(name = "quantity")
    val quantity: String = "",
    @ColumnInfo(name = "totalPrice")
    val totalPrice: Double = 0.0,
    @ColumnInfo(name = "productId")
    val referenceProductId: Long,
)

@Entity(primaryKeys = ["productCartId", "productId"])
data class ProductCartCrossRef(
    val productCartId: Long,
    @ColumnInfo(index = true)
    val productId: Long
)

data class ProductCartWithProductAndUnit(
    @Embedded val productCart: ProductCart,
    @Relation(
        entity = Product::class,
        parentColumn = "productId",
        entityColumn = "productId"
    )
    val productWithUnit: ProductWithUnit
)
