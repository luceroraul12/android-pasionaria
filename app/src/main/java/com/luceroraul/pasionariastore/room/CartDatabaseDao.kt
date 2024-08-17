package com.luceroraul.pasionariastore.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.luceroraul.pasionariastore.model.Cart
import com.luceroraul.pasionariastore.model.CartWithData
import com.luceroraul.pasionariastore.model.Product
import com.luceroraul.pasionariastore.model.ProductCart
import com.luceroraul.pasionariastore.model.ProductCartWithData
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

    @Query("""
        SELECT p.*
        FROM ProductCart pc
            INNER JOIN product p ON p.product_id = pc.product_id
        GROUP BY p.name 
        ORDER BY COUNT(p.product_id) DESC
        LIMIT 10
    """)
    fun getTopProducts(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProduct(product: ProductCart)

    @Update
    fun updateCartProduct(productCart: ProductCart)

    @Delete
    suspend fun deleteProductCart(product: ProductCart)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: Cart)

    @Update
    suspend fun updateCart(cart: Cart)

    @Delete
    suspend fun deleteCart(cart: Cart)

    @Query("UPDATE cart SET backend_cart_id = :backendCartId, status = :status WHERE cart_id = :cartId ")
    fun updateCartIdsAndStatus(cartId: Long, backendCartId: Long, status: String)

    @Query("UPDATE productcart SET backend_product_cart_id = :backendCartProductId WHERE product_cart_id = :cartProductId ")
    fun updateCartProductIds(cartProductId: Long, backendCartProductId: Long)

    @Update
    fun updateCartProducts(cartProducts: MutableList<ProductCart>)

}