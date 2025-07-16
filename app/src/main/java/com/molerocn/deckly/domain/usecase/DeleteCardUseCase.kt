package com.molerocn.deckly.domain.usecase

import com.molerocn.deckly.data.repository.CardRepository
import com.molerocn.deckly.data.repository.DeckRepository
import com.molerocn.deckly.domain.model.CardModel
import com.molerocn.deckly.domain.model.Deck
import javax.inject.Inject

class DeleteCardUseCase @Inject constructor(private val repository: CardRepository) {

    suspend operator fun invoke(card: CardModel) {
        return repository.deleteCards(card)
    }
}
