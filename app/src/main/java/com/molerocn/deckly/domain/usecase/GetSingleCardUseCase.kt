package com.molerocn.deckly.domain.usecase

import com.molerocn.deckly.data.repository.CardRepository
import com.molerocn.deckly.domain.model.CardModel
import javax.inject.Inject

class GetSingleCardUseCase @Inject constructor(private val repository: CardRepository) {

    suspend operator fun invoke(cardId: Int): CardModel? {
        return repository.getSingleCardById(cardId)
    }
}