package com.luceroraul.pasionariastore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luceroraul.pasionariastore.usecase.ProductSynchronizer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CheckDatabaseViewModel @Inject constructor(
    private val productSynchronizer: ProductSynchronizer
) : ViewModel() {

    fun syncProducts(onChangeLoading: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            onChangeLoading(true)
            val result = withContext(Dispatchers.IO) {
                productSynchronizer.syncSystem()
            }
            onChangeLoading(false)
        }
    }
}