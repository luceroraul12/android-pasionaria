package com.example.pasionariastore.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.pasionariastore.model.Product
import com.example.pasionariastore.model.ProductWithUnit
import com.example.pasionariastore.model.Unit
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDatabaseDao {
    @Query("SELECT * FROM Product p")
    fun getProductsWithUnit(): Flow<List<ProductWithUnit>>

    @Query("SELECT * FROM Product p")
    fun getProducts(): Flow<List<Product>>

    @Query("SELECT * FROM Product p where p.product_id = :id")
    fun getProductsWithUnitById(id: Long): Flow<ProductWithUnit>

    @Query(
        """
        SELECT *
        FROM product p
        WHERE 
            (p.name like :search OR p.description like :search)
            AND p.product_id NOT IN 
                (SELECT pr.product_id
                FROM product pr 
                    INNER JOIN ProductCart pc ON pc.product_id = pr.product_id 
                    WHERE pc.cart_id = :cartId)
    """
    )
    fun getProductsBySearch(search: String, cartId: Long): Flow<List<ProductWithUnit>>

    @Query("SELECT * FROM Unit")
    fun getUnits(): Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Upsert
    suspend fun insertUnits(units: List<Unit>)

    @Upsert
    suspend fun insertProducts(products: List<Product>)
}