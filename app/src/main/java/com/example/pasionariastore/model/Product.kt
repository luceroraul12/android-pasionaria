package com.example.pasionariastore.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

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
    val name: String = "SIN NOMBRE",
    @ColumnInfo(name = "description")
    val description: String = "SIN DESCRIPCION",
    @ColumnInfo(name = "price_list")
    val priceList: Double = 0.0,
    @ColumnInfo(name = "unit_id")
    val unitId: Long = 0,
    @ColumnInfo(name = "last_update")
    val lastUpdate: Date? = null
)

fun Date.format(): String {
    val df: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
    return df.format(this)
}
