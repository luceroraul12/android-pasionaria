package com.example.pasionariastore.room

import androidx.room.Dao
import androidx.room.Query
import com.example.pasionariastore.model.ProductHasUnit

@Dao
interface ProductDatabaseDao {
    @Query("SELECT * FROM Product")
    fun getProducts(): List<ProductHasUnit>
}