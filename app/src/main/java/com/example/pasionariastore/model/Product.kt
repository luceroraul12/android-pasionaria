package com.example.pasionariastore.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    val productId: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String = "SIN DESCRIPCION",
    @ColumnInfo(name = "price_list")
    val priceList: Double,
    // VER
    @ColumnInfo(name = "unit_id")
    val unitId: Long
)

@Entity
data class Unit(
    @PrimaryKey(autoGenerate = true)
    val unitId: Long = 0,
    @ColumnInfo(name = "name_unit")
    val name: String,
    @ColumnInfo(name = "name_type")
    val nameType: String,
    @ColumnInfo(name = "value")
    val value: Double
)

//data class ProductHasUnit(
//    @Embedded val product: Product,
//    @Relation(
//        parentColumn = "productId",
//        entityColumn = "unitId"
//    )
//    val unit: Unit
//)

@Entity(primaryKeys = ["productId", "unitId"])
data class ProductHasUnit(
    val productId: Long,
    val unitId: Long
)
