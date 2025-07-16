package com.molerocn.deckly.presentation.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.dataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molerocn.deckly.data.preferences.DataStoreManager
import com.molerocn.deckly.domain.model.Deck
import com.molerocn.deckly.domain.usecase.AddDeckUseCase
import com.molerocn.deckly.domain.usecase.DeleteDeckUseCase
import com.molerocn.deckly.domain.usecase.UpdateDeckUseCase
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
    private val addDeckUseCase: AddDeckUseCase,
    private val updateDeckUseCase: UpdateDeckUseCase,
    private val deleteDeckUseCase: DeleteDeckUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _imageUrl = MutableStateFlow("")
    val imageUrl: StateFlow<String> = _imageUrl.asStateFlow()

    var showDeckError by mutableStateOf(false)

    private val _deckItems = MutableStateFlow<List<Deck>>(emptyList())
    val deckItems: StateFlow<List<Deck>> = _deckItems.asStateFlow()

    private val _mountCards = MutableStateFlow(0)
    val mountCards: StateFlow<Int> = _mountCards.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _imageUrl.value = dataStoreManager.getImageUrl()
            _deckItems.value = getDecksUseCase(withCardsCount = true)
            _deckItems.value.map { deck ->
                Log.i("", "description: ${deck.description}")
            }
            _isLoading.value = false
            _mountCards.value = _deckItems.value.sumOf { it.amountOfCardsToBeStudy }
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
                _deckItems.value = _deckItems.value.toMutableList().apply {
                    add(response)
                }
            } else {
                showDeckError = true
            }
        }
    }

    fun deleteDeck(deck: Deck) {
        viewModelScope.launch {
            deleteDeckUseCase(deck)
            _deckItems.value = _deckItems.value.toMutableList().apply {
                remove(deck)
            }
        }
    }

    fun editDeck(modifiedDeck: Deck, originalDeck: Deck) {
        viewModelScope.launch {
            if (originalDeck.name == modifiedDeck.name && originalDeck.description != modifiedDeck.description) {
                updateDeckUseCase(modifiedDeck)
                updateDeck(modifiedDeck)
            } else if (originalDeck.name != modifiedDeck.name) {
                val response = updateDeckUseCase(modifiedDeck, withNameValidation = true)
                if (response) {
                    updateDeck(modifiedDeck)
                } else {
                    showDeckError = true
                }
            }
        }
    }

    private fun updateDeck(deck: Deck) {
        _deckItems.value = _deckItems.value.map { deckFound ->
            if (deckFound.id == deck.id) {
                deckFound.copy(name = deck.name, description = deck.description)
            } else {
                deckFound
            }
        }
    }

    fun sync() {
        print("syncing...")
    }
}
//     fun addCardEvent(deckId: Int) {
//         val updatedList = _deckItems.value.map { deck ->
//             if (deck.id == deckId) {
//                 Log.i("", "agregando un card a amount of cards to be study")
//                 deck.copy(amountOfCardsToBeStudy = deck.amountOfCardsToBeStudy + 1)
//             } else {
//                 deck
//             }
//         }
//         _deckItems.value = updatedList
//     }

