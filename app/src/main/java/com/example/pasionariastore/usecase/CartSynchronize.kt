package com.example.pasionariastore.usecase

import com.example.pasionariastore.model.CartWithData
import com.example.pasionariastore.repository.BackendRepository
import com.example.pasionariastore.repository.CartRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartSynchronize @Inject constructor(
    private val backendRepository: BackendRepository,
    private val cartRepository: CartRepository
) {

    suspend fun trySynchronizeCarts(data: List<CartWithData>){
        // Mando a guardar los carts al servidor
        // Al retornar actualizo los carts en local cambiando el estado y guardando el id del servidor
    }
}