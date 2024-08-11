package com.example.pasionariastore.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pasionariastore.converter.Converters
import com.example.pasionariastore.model.Cart
import com.example.pasionariastore.model.Product
import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.Unit

@Database(
    entities = [Product::class, Unit::class, Cart::class, ProductCart::class],
    version = 6,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PasionariaDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDatabaseDao
    abstract fun cartDao(): CartDatabaseDao
}