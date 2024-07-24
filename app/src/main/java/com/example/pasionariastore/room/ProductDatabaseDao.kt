package com.example.pasionariastore.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.pasionariastore.model.ProductHasUnit

@Dao
interface ProductDatabaseDao {
    @Transaction
    @Query("SELECT * FROM Product")
    fun getProducts(): List<ProductHasUnit>
}