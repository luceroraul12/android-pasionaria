package com.example.pasionariastore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasionariastore.repository.ProductRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@ViewModelScoped
class CheckDatabaseViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    init {
        checkData()
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