package com.example.pasionariastore.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "product",
    foreignKeys = [
        ForeignKey(entity = Unit::class, parentColumns = ["unit_id"], childColumns = ["unit_id"])
    ],
    indices = [Index("unit_id")]
)
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id")
    val productId: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String = "SIN DESCRIPCION",
    @ColumnInfo(name = "price_list")
    val priceList: Double,
    @ColumnInfo(name = "unit_id")
    val unitId: Long
)

