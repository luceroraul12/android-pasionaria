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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Dao
interface ProductDatabaseDao {
    @Transaction
    @Query("SELECT * FROM Product")
    fun getProducts(): Flow<List<ProductWithUnit>>

    @Transaction
    @Query("""
        SELECT *
        FROM Product
        WHERE 
            name like :search OR description like :search
    """)
    fun getProductsBySearch(search: String): Flow<List<ProductWithUnit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductWithUnit(productUnitCrossRef: ProductUnitCrossRef)
}