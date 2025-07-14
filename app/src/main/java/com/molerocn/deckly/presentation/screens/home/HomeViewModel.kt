package com.molerocn.deckly.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molerocn.deckly.domain.model.Deck
import com.molerocn.deckly.domain.usecase.AddDeckUseCase
import com.molerocn.deckly.domain.usecase.GetDecksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDecksUseCase: GetDecksUseCase,
    private val addDeckUseCase: AddDeckUseCase
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    var showDeckError by mutableStateOf(false)

    private val _deckItems = MutableStateFlow<List<Deck>>(emptyList())
    val deckItems: StateFlow<List<Deck>> = _deckItems.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _deckItems.value = getDecksUseCase()
            _isLoading.value = false
        }
    }

    fun addDeck(name: String, description: String) {
        viewModelScope.launch {
            val newDeck = Deck(
                name = name,
                description = description
            )
            val response = addDeckUseCase(newDeck)
            if (response != null) {
                loadData()
            } else {
                showDeckError = true
            }
        }
    }
}