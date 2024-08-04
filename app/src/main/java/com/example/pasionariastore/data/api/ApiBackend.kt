package com.example.pasionariastore.data.api

import com.example.pasionariastore.model.BackendProduct
import retrofit2.Response
import retrofit2.http.GET

interface ApiBackend {

    @GET("/customer/products")
    suspend fun getCustomerProducts(): Response<List<BackendProduct>>
}