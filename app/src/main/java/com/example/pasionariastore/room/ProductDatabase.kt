package com.example.pasionariastore.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pasionariastore.model.Product
import com.example.pasionariastore.model.ProductHasUnit
import com.example.pasionariastore.model.Unit

@Database(entities = [Product::class, Unit::class, ProductHasUnit::class], version = 1, exportSchema = false)
abstract class PasionariaDatabase : RoomDatabase() {
    abstract fun productDao() : ProductDatabaseDao
}