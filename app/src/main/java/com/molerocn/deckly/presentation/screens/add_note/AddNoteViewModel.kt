package com.molerocn.deckly.presentation.screens.add_note

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molerocn.deckly.domain.model.CardModel
import com.molerocn.deckly.domain.model.Deck
import com.molerocn.deckly.domain.usecase.AddCardUseCase
import com.molerocn.deckly.domain.usecase.GetDecksUseCase
import com.molerocn.deckly.presentation.screens.home.HomeViewModel
import com.molerocn.deckly.presentation.screens.login.LoginScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val addCardUseCase: AddCardUseCase,
    private val getDecksUseCase: GetDecksUseCase,
) : ViewModel() {

    var mazos by mutableStateOf<List<Deck>>(emptyList())
        private set

    var selectedDeck by mutableStateOf<Deck?>(null)
    var frontText by mutableStateOf("")
    var backText by mutableStateOf("")
    var showDialog by mutableStateOf(false)
    var dialogMessage by mutableStateOf("")

    init {
        viewModelScope.launch {
            val decks = getDecksUseCase() // Assuming it returns List<Deck>
            mazos = decks
            selectedDeck = decks.firstOrNull()
        }
    }

    fun onSave() {
        viewModelScope.launch {
            selectedDeck?.let { deck ->
                val card = CardModel(
                    deckId = deck.id,
                    front = frontText,
                    back = backText
                )
                val response = addCardUseCase(card)
                if (response != null) {
                    // Reset UI
                    frontText = ""
                    backText = ""
                    selectedDeck = mazos.firstOrNull()
                    dialogMessage = "Tarjeta creada con éxito"
                } else {
                    dialogMessage = "Ya existe una tarjeta con ese anverso"
                }
                showDialog = true
            }
        }
    }

    fun changeMazo(deck: Deck) {
        selectedDeck = deck
    }
}

