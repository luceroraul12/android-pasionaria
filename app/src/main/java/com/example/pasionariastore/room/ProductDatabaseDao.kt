package com.example.pasionariastore.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.pasionariastore.model.Product
import com.example.pasionariastore.model.ProductUnitCrossRef
import com.example.pasionariastore.model.ProductWithUnit
import com.example.pasionariastore.model.Unit

@Dao
interface ProductDatabaseDao {
    @Transaction
    @Query("SELECT * FROM Product")
    fun getProducts(): List<ProductWithUnit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductWithUnit(productUnitCrossRef: ProductUnitCrossRef)
}