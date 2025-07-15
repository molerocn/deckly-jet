package com.molerocn.deckly.presentation.screens.study_card

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molerocn.deckly.domain.model.CardModel
import com.molerocn.deckly.domain.usecase.GetCardsUseCase
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
class StudyCardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCardsUseCase: GetCardsUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val deckId: Int = checkNotNull(savedStateHandle["deckId"])

    private val _cardsItems = MutableStateFlow<List<CardModel>>(emptyList())
    val cardsItems: StateFlow<List<CardModel>> = _cardsItems.asStateFlow()

    private val _currentCard = MutableStateFlow<CardModel?>(null)
    val currentCard: StateFlow<CardModel?> = _currentCard.asStateFlow()

    init {
        loadCards()
    }

    fun loadCards() {
        viewModelScope.launch {
            _cardsItems.value = getCardsUseCase(deckId)
            _currentCard.value = _cardsItems.value[0]
            _isLoading.value = false
        }
    }

    fun study(option: Difficult) {
        when (option) {
            Difficult.AGAIN -> {
            }
            Difficult.HARD -> TODO()
            Difficult.GOOD -> TODO()
            Difficult.EASY -> TODO()
        }
    }

    fun hard() {
    }

    fun good() {
    }

    fun easy() {
    }

    fun goBackInHistory() {
    }
}
