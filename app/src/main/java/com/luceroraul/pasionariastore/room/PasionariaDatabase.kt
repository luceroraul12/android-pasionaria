package com.luceroraul.pasionariastore.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.luceroraul.pasionariastore.converter.Converters
import com.luceroraul.pasionariastore.model.Cart
import com.luceroraul.pasionariastore.model.Product
import com.luceroraul.pasionariastore.model.ProductCart
import com.luceroraul.pasionariastore.model.Unit

@Database(
    entities = [Product::class, Unit::class, Cart::class, ProductCart::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PasionariaDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDatabaseDao
    abstract fun cartDao(): CartDatabaseDao
}