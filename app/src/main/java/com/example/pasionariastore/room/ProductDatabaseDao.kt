package com.example.pasionariastore.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pasionariastore.model.Product
import com.example.pasionariastore.model.ProductWithUnit
import com.example.pasionariastore.model.Unit
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDatabaseDao {
    @Query(
        """
        SELECT *
        FROM Product p
            """
    )
    fun getProductsWithUnit(): Flow<List<ProductWithUnit>>

    @Query("SELECT * FROM Product")
    fun getProducts(): Flow<List<Product>>

    @Query(
        """
        SELECT *
        FROM Product p
            INNER JOIN Unit u ON u.unit_id = p.unit_id
        WHERE 
            name like :search OR description like :search
    """
    )
    fun getProductsBySearch(search: String): Flow<List<ProductWithUnit>>

    @Query("SELECT * FROM Unit")
    fun getUnits(): Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnits(units: List<Unit>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)
}