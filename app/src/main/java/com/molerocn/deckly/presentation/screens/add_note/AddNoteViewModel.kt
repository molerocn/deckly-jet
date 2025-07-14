package com.molerocn.deckly.presentation.screens.add_note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molerocn.deckly.domain.model.Card
import com.molerocn.deckly.domain.model.Deck
import com.molerocn.deckly.domain.usecase.AddCardUseCase
import com.molerocn.deckly.domain.usecase.GetDecksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val addCardUseCase: AddCardUseCase,
    private val getDecksUseCase: GetDecksUseCase
) : ViewModel() {

    var mazos by mutableStateOf<List<Deck>>(emptyList())
        private set

    var selectedDeck by mutableStateOf<Deck?>(null)
    var frontText by mutableStateOf("")
    var backText by mutableStateOf("")
    var showDialog by mutableStateOf(false)

    init {
        viewModelScope.launch {
            val decks = getDecksUseCase() // Assuming it returns List<Deck>
            mazos = decks
            selectedDeck = decks.firstOrNull()
        }
    }

    fun onGuardar() {
        viewModelScope.launch {
            selectedDeck?.let { deck ->
                val card = Card(
                    deckId = deck.id,
                    front = frontText,
                    back = backText
                )
                addCardUseCase(card)

                // Reset UI
                frontText = ""
                backText = ""
                selectedDeck = mazos.firstOrNull()
                showDialog = true
            }
        }
    }

    fun dismissDialog() {
        showDialog = false
    }

    fun changeMazo(deck: Deck) {
        selectedDeck = deck
    }
}

