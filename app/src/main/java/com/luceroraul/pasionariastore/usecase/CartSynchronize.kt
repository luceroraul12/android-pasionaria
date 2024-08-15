package com.luceroraul.pasionariastore.usecase

import com.luceroraul.pasionariastore.model.BackendCart
import com.luceroraul.pasionariastore.model.BackendCartProduct
import com.luceroraul.pasionariastore.model.Cart
import com.luceroraul.pasionariastore.model.CartWithData
import com.luceroraul.pasionariastore.model.ProductCart
import com.luceroraul.pasionariastore.model.toBackendData
import com.luceroraul.pasionariastore.repository.BackendRepository
import com.luceroraul.pasionariastore.repository.CartRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartSynchronize @Inject constructor(
    private val backendRepository: BackendRepository,
    private val cartRepository: CartRepository
) {

    suspend fun trySynchronizeCarts(carts: List<CartWithData>){
        // Mando a guardar los carts al servidor
        val convertedCarts: List<BackendCart> = carts.map { c -> c.toBackendData() }
        val response: List<BackendCart>? = backendRepository.synchronizeCarts(convertedCarts)
        response?.forEach {
            cartRepository.updateCartIdsAndStatus(
                cartId = it.cartId,
                backendCartId = it.backendCartId!!,
                status = it.status!!
            );
            it.products.forEach {
                cartRepository.updateCartProductIds(
                    cartProductId = it.cartProductId,
                    backendCartProductId = it.backendCartProductId!!
                )
            }
        }
    }
}