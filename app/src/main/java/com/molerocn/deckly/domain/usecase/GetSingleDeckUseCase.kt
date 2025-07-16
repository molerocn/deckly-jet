package com.molerocn.deckly.domain.usecase

import com.molerocn.deckly.data.repository.CardRepository
import com.molerocn.deckly.data.repository.DeckRepository
import com.molerocn.deckly.domain.model.Deck
import javax.inject.Inject

class GetSingleDeckUseCase @Inject constructor(
    private val deckRepository: DeckRepository,
) {

    suspend operator fun invoke(deckId: Int): Deck {
        val deck = deckRepository.getDeckFromDatabase(deckId)
        return deck
    }
}
