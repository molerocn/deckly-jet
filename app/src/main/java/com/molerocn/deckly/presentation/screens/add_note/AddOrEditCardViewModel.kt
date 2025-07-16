package com.molerocn.deckly.presentation.screens.add_note

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molerocn.deckly.domain.model.CardModel
import com.molerocn.deckly.domain.model.Deck
import com.molerocn.deckly.domain.usecase.AddCardUseCase
import com.molerocn.deckly.domain.usecase.GetDecksUseCase
import com.molerocn.deckly.domain.usecase.UpdateCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddOrEditCardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addCardUseCase: AddCardUseCase,
    private val updateCardUseCase: UpdateCardUseCase,
    private val getDecksUseCase: GetDecksUseCase,
) : ViewModel() {

    val cardIdForEdit: Int? = savedStateHandle.get<Int>("cardId")?.takeIf { it != -1 }
    val isForEdit = cardIdForEdit != null
    val deckIdForEdit: Int? = savedStateHandle.get<Int>("deckId")?.takeIf { it != -1 }
    val frontForEdit: String? = savedStateHandle.get<String>("front")?.takeIf { it.isNotBlank() }
    val backForEdit: String? = savedStateHandle.get<String>("back")?.takeIf { it.isNotBlank() }

    var decks by mutableStateOf<List<Deck>>(emptyList())
        private set

    private val _cardAdded = MutableStateFlow(false)
    val cardAdded: StateFlow<Boolean> = _cardAdded.asStateFlow()

    var selectedDeck by mutableStateOf<Deck?>(null)
    var frontText by mutableStateOf("")
    var backText by mutableStateOf("")
    var successAction by mutableStateOf(false)
    var dialogMessage by mutableStateOf("")

    init {
        viewModelScope.launch {
            if (isForEdit) {
                Log.i("", "es para editar")
                frontText = frontForEdit!!
                backText = backForEdit!!
            } else {
                decks = getDecksUseCase(withCardsCount = false)
                if (decks.isNotEmpty()) {
                    selectedDeck = decks[0]
                }
            }
        }
    }

    fun onSubmit() {
        viewModelScope.launch {
            dialogMessage = ""
            if (!isForEdit) {
                selectedDeck?.let { deck ->
                    val card = CardModel(
                        deckId = deck.id,
                        front = frontText,
                        back = backText
                    )
                    val response = addCardUseCase(card)
                    if (response != null) {
                        frontText = ""
                        backText = ""
                        selectedDeck = decks.firstOrNull()
                        dialogMessage = "Tarjeta creada con éxito"
                        _cardAdded.value = true
                    } else {
                        dialogMessage = "Ya existe una tarjeta con ese anverso"
                    }
                    successAction = true
                }
            } else {
                val card = CardModel(
                    id = cardIdForEdit!!,
                    deckId = deckIdForEdit!!,
                    front = frontText,
                    back = backText
                )
                if (frontForEdit != frontText || backForEdit != backText) {
                    updateCardUseCase(card, withFrontValidation = true)
                } else if (frontForEdit == frontText && backForEdit != backText) {
                    val response = updateCardUseCase(card, withFrontValidation = false)
                    if (!response) {
                        dialogMessage = "Ya existe una tarjeta con ese anverso"
                    }
                }
                successAction = true
            }
        }
    }

    fun changeMazo(deck: Deck) {
        selectedDeck = deck
    }
}

