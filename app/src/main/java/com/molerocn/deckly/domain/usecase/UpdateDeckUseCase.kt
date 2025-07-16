package com.molerocn.deckly.domain.usecase

import android.util.Log
import com.molerocn.deckly.data.repository.DeckRepository
import com.molerocn.deckly.domain.model.Deck
import javax.inject.Inject

class UpdateDeckUseCase @Inject constructor(private val repository: DeckRepository) {

    suspend operator fun invoke(deck: Deck, withNameValidation: Boolean = false): Boolean {
        Log.i("", "deckid: ${deck.id}")
        Log.i("", "name: ${deck.name}")
        Log.i("", "description: ${deck.description}")
        if (withNameValidation && repository.deckExistWithSameName(deck.name)) {
            return false
        }
        repository.updateDeck(deck)
        return true
    }
}
