package com.example.pasionariastore.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.ProductCartWithData
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDatabaseDao {
    @Transaction
    @Query("SELECT * FROM ProductCart")
    fun getCartProducts(): Flow<List<ProductCartWithData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProduct(product: ProductCart)

    @Delete
    suspend fun deleteProductCart(product: ProductCart)

}