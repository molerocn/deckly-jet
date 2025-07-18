package com.molerocn.deckly.domain.usecase

import com.molerocn.deckly.data.repository.CardRepository
import com.molerocn.deckly.domain.model.CardModel
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(private val repository: CardRepository) {

    suspend operator fun invoke(deckId: Int): List<CardModel> {
        return repository.getCardsByDeckFromDatabase(deckId)
    }
}