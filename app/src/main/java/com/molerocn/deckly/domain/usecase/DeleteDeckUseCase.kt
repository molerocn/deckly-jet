package com.molerocn.deckly.domain.usecase

import com.molerocn.deckly.data.repository.DeckRepository
import com.molerocn.deckly.domain.model.Deck
import javax.inject.Inject

class DeleteDeckUseCase @Inject constructor(private val repository: DeckRepository) {

    suspend operator fun invoke(deck: Deck) {
        return repository.deleteDeckFromDatabase(deck)
    }
}
