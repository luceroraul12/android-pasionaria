package com.example.pasionariastore.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.ProductCartCrossRef
import com.example.pasionariastore.model.ProductCartWithProductAndUnit
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDatabaseDao {
    @Transaction
    @Query("SELECT * FROM ProductCart")
    fun getCartProducts(): Flow<List<ProductCartWithProductAndUnit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProduct(product: ProductCart)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProductCross(productCartCrossRef: ProductCartCrossRef)

}