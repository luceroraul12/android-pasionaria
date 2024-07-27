package com.example.pasionariastore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasionariastore.repository.ProductRepositoryImpl
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@ViewModelScoped
class CheckDatabaseViewModel @Inject constructor(
    private val productRepositoryImpl: ProductRepositoryImpl
) : ViewModel() {
    init {
        checkData()
    }

    fun checkData() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepositoryImpl.getUnits().collect {
                if (it.isNullOrEmpty())
                    productRepositoryImpl.saveFirstUnits()
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            productRepositoryImpl.getProductsWithUnit().collect {
                if (it.isNullOrEmpty())
                    productRepositoryImpl.saveFirstProducts()
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            productRepositoryImpl.getProductsWithUnit().collect {
                if (it.isNullOrEmpty())
                    productRepositoryImpl.saveFirstProducts()
            }
        }
    }
}