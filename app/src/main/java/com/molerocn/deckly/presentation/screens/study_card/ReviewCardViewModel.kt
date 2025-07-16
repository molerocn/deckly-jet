package com.molerocn.deckly.presentation.screens.study_card

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molerocn.deckly.domain.model.CardModel
import com.molerocn.deckly.domain.usecase.DeleteCardUseCase
import com.molerocn.deckly.domain.usecase.GetCardsUseCase
import com.molerocn.deckly.domain.usecase.ReviewCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class Difficult {
    AGAIN,
    HARD,
    GOOD,
    EASY
}

@HiltViewModel
class ReviewCardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCardsUseCase: GetCardsUseCase,
    private val reviewCardUseCase: ReviewCardUseCase,
    private val deleteCardUseCase: DeleteCardUseCase
) : ViewModel() {

    // Agrega estas variables en tu ViewModel
    private val historyStack = mutableListOf<CardModel>()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _hasHistoryStack = MutableStateFlow(false)
    val hasHistoryStack: StateFlow<Boolean> = _hasHistoryStack.asStateFlow()

    private val _isAnswerRevealed = MutableStateFlow(false)
    val isAnswerRevealed: StateFlow<Boolean> = _isAnswerRevealed.asStateFlow()

    private val _isThereNoCards = MutableStateFlow(false)
    val isThereNoCards: StateFlow<Boolean> = _isThereNoCards.asStateFlow()

    val deckId: Int = checkNotNull(savedStateHandle["deckId"])

    private val _cardsItems = MutableStateFlow<List<CardModel>>(emptyList())

    private val _currentCard = MutableStateFlow<CardModel?>(null)
    val currentCard: StateFlow<CardModel?> = _currentCard.asStateFlow()

    init {
        loadCards()
    }

    fun loadCards() {
        viewModelScope.launch {
            val cards = getCardsUseCase(deckId)
            _cardsItems.value = cards

            if (cards.isNotEmpty()) {
                _currentCard.value = cards[0]
            } else {
                _isThereNoCards.value = true
            }

            _isLoading.value = false
        }
    }

    fun revealAnswer() {
        _isAnswerRevealed.value = true
    }

    fun reviewCard(option: Difficult) {
        viewModelScope.launch {
            val current = _currentCard.value ?: return@launch

            historyStack.add(current)

            val updatedCard = reviewCardUseCase(current, option) ?: return@launch

            val updatedList = _cardsItems.value.toMutableList().apply {
                if (updatedCard.due < System.currentTimeMillis()) {
                    add(updatedCard)
                }
                remove(current)
            }
            _cardsItems.value = updatedList
            _hasHistoryStack.value = true

            _currentCard.value = updatedList.firstOrNull { it.id != current.id }
            _isAnswerRevealed.value = false

            if (_cardsItems.value.isEmpty()) {
                _isThereNoCards.value = true
            }
        }

    }

    fun deleteCard() {
        viewModelScope.launch {
            deleteCardUseCase(_currentCard.value!!)

            _cardsItems.value = _cardsItems.value.toMutableList().apply {
                remove(_currentCard.value!!)
            }

            if (_cardsItems.value.isEmpty()) {
                _isThereNoCards.value = true
            }

            _currentCard.value = _cardsItems.value.firstOrNull { it.id != _currentCard.value!!.id }
        }
    }

    fun goBackInHistory() {
        viewModelScope.launch {
            if (historyStack.isNotEmpty()) {
                val previousCard = historyStack[historyStack.size - 1]
                historyStack.remove(previousCard)
                _hasHistoryStack.value = historyStack.isNotEmpty()
                var updatedList: List<CardModel>

                if (!_cardsItems.value.contains(previousCard)) {
                    // si fue eliminado de la lista para revision
                    updatedList = _cardsItems.value.toMutableList().apply {
                        add(0, previousCard)
                    }
                } else {
                    // si no fue eliminado de la lista para revision
                    val updatedCard: CardModel = _cardsItems.value.last()
                    updatedList = _cardsItems.value.toMutableList().apply {
                        remove(updatedCard)
                        add(0, previousCard)
                    }
                }
                _cardsItems.value = updatedList
                _currentCard.value = updatedList.first()
                _isThereNoCards.value = false
                _isAnswerRevealed.value = false
            }
        }
    }

    fun changeCurrentCardAfterEdit(front: String, back: String) {
        _currentCard.value = _currentCard.value!!.copy(front = front, back = back)
    }
}
