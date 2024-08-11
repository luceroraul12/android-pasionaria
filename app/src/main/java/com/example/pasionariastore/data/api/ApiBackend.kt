package com.example.pasionariastore.data.api

import com.example.pasionariastore.model.BackendCart
import com.example.pasionariastore.model.BackendLogin
import com.example.pasionariastore.model.BackendLoginResponse
import com.example.pasionariastore.model.BackendProduct
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiBackend {

    @GET("/customer/products")
    suspend fun getCustomerProducts(): Response<List<BackendProduct>>

    @POST("/login")
    suspend fun login(@Body data: BackendLogin): Response<BackendLoginResponse>

    @POST("/cart")
    suspend fun synchronizeCarts(@Body convertedCarts: List<BackendCart>): Response<List<BackendCart>>
}