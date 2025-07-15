package com.molerocn.deckly.domain.usecase

import com.molerocn.deckly.data.repository.CardRepository
import com.molerocn.deckly.data.repository.DeckRepository
import com.molerocn.deckly.domain.model.Deck
import javax.inject.Inject

class GetDecksUseCase @Inject constructor(
    private val deckRepository: DeckRepository,
    private val cardRepository: CardRepository
) {

    suspend operator fun invoke(): List<Deck> {
        val decks = deckRepository.getAllDecksFromDatabase()
        return decks.map { deck ->
            Deck(
                id = deck.id,
                name = deck.name,
                description = deck.description,
                amountOfCardsToBeStudy = cardRepository.amountOfDueCardsByDeck(deck.id)
            )
        }
    }
}