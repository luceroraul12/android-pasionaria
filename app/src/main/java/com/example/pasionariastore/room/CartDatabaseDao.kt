package com.example.pasionariastore.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.pasionariastore.model.Cart
import com.example.pasionariastore.model.CartWithData
import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.ProductCartWithData
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDatabaseDao {
    @Transaction
    @Query("SELECT * FROM Cart WHERE cart_id = :cartId")
    fun getCartWithData(cartId: Long): Flow<CartWithData>

    @Transaction
    @Query("SELECT * FROM Cart WHERE status IN (:status) ORDER BY date_created DESC")
    fun getCartsWithStatus(status: List<String>): Flow<List<CartWithData>>

    @Transaction
    @Query("SELECT * FROM ProductCart")
    fun getCartProducts(): Flow<List<ProductCartWithData>>

    @Transaction
    @Query("SELECT * FROM ProductCart WHERE product_cart_id = :productCartId")
    fun getProductCartWithDataById(productCartId: Long): Flow<ProductCartWithData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProduct(product: ProductCart)

    @Update
    fun updateCartProduct(productCart: ProductCart)


    @Delete
    suspend fun deleteProductCart(product: ProductCart)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: Cart)

    @Delete
    suspend fun deleteCart(cart: Cart)

}