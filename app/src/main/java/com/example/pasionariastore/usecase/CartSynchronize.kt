package com.example.pasionariastore.usecase

import com.example.pasionariastore.model.BackendCart
import com.example.pasionariastore.model.BackendCartProduct
import com.example.pasionariastore.model.Cart
import com.example.pasionariastore.model.CartWithData
import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.toBackendData
import com.example.pasionariastore.repository.BackendRepository
import com.example.pasionariastore.repository.CartRepository
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