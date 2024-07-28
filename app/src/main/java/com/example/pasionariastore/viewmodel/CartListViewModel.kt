package com.example.pasionariastore.viewmodel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.pasionariastore.model.state.CartListUIState
import com.example.pasionariastore.model.state.CartStatus
import com.example.pasionariastore.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CartListViewModel @Inject constructor(
    private val cartRepository: CartRepository

) : ViewModel() {
    private var _state = MutableStateFlow(CartListUIState())
    val state = _state.asStateFlow()


    fun updateChipStatus(chip: CartStatus): Unit {
        val index: Int = _state.value.stateFilters.indexOf(chip)
        val statues: MutableList<CartStatus> = _state.value.stateFilters
        statues.set(index, chip.apply {
            enabled = !enabled
        })
        _state.update {
            it.copy(
                stateFilters = statues.toMutableStateList()
            )
        }
    }
}