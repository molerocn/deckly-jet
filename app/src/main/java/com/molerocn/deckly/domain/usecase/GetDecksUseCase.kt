package com.molerocn.deckly.domain.usecase

import android.util.Log
import com.molerocn.deckly.data.repository.CardRepository
import com.molerocn.deckly.data.repository.DeckRepository
import com.molerocn.deckly.domain.model.Deck
import javax.inject.Inject

class GetDecksUseCase @Inject constructor(
    private val deckRepository: DeckRepository,
    private val cardRepository: CardRepository
) {

    suspend operator fun invoke(withCardsCount: Boolean): List<Deck> {
        val decks = deckRepository.getAllDecksFromDatabase()
        Log.i("", "actuando aqui en get decks")
        return decks.map { deck ->
            if (withCardsCount) {
                deck.copy(amountOfCardsToBeStudy = cardRepository.amountOfDueCardsByDeck(deck.id))
            } else {
                deck
            }
        }
    }
}