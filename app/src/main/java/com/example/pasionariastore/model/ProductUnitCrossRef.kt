package com.example.pasionariastore.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(primaryKeys = ["product_id", "unit_id"], indices = [Index("unit_id")])
data class ProductUnitCrossRef(
    @ColumnInfo(name = "product_id")
    val productId: Long,
    @ColumnInfo(name = "unit_id")
    val unitId: Long
)