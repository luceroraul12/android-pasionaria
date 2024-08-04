package com.example.pasionariastore.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasionariastore.repository.BackendRepositoryImpl
import com.example.pasionariastore.repository.ProductRepository
import com.example.pasionariastore.usecase.ProductSynchronizer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CheckDatabaseViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val backendRepositoryImpl: BackendRepositoryImpl,
    private val productSynchronizer: ProductSynchronizer
) : ViewModel() {
    init {
        checkBackendStates()
        syncData()
    }

    private fun syncData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                productSynchronizer.syncSystem()
            }
        }
    }

    private fun checkBackendStates() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = backendRepositoryImpl.getCustomerProducts()
                Log.i("backend", response.toString())
            }
        }
    }

    fun checkData() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getUnits().collect {
                if (it.isNullOrEmpty())
                    productRepository.saveFirstUnits()
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getProducts().collect {
                if (it.isNullOrEmpty())
                    productRepository.saveFirstProducts()
            }
        }
    }
}