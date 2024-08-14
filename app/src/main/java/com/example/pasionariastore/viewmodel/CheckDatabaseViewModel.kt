package com.example.pasionariastore.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasionariastore.repository.BackendRepository
import com.example.pasionariastore.usecase.ProductSynchronizer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CheckDatabaseViewModel @Inject constructor(
    private val backendRepository: BackendRepository,
    private val productSynchronizer: ProductSynchronizer
) : ViewModel() {
    init {
        checkBackendStates()
        syncProducts()
    }

    private fun syncProducts(onChangeLoading: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            onChangeLoading(true)
            val result = withContext(Dispatchers.IO) {
                productSynchronizer.syncSystem()
            }
            onChangeLoading(false)
        }
    }

    private fun checkBackendStates() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                val response = backendRepository.getCustomerProducts()
                Log.i("backend", response.toString())
            }
        }
    }

    fun syncProductsWithLoading(
        onChangeLoading: (Boolean) -> Unit
    ) {
        syncProducts(onChangeLoading = onChangeLoading)
    }
}