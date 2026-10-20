package com.example.laboratorio1

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

class CounterViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val count: StateFlow<Int> =
        savedStateHandle.getStateFlow("counter_key", 0)

    fun increment() {
        savedStateHandle["counter_key"] = count.value + 1
    }
}