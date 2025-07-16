package com.molerocn.deckly.domain.usecase

import com.molerocn.deckly.data.repository.DeckRepository
import com.molerocn.deckly.domain.model.Deck
import javax.inject.Inject

class AddDeckUseCase @Inject constructor(private val repository: DeckRepository) {

    // TODO: agregar logica para decidir si obtener los datos desde api o local database
    suspend operator fun invoke(deck: Deck): Deck? {
        if (repository.deckExistWithSameName(deck.name)) {
            return null
        }
        return repository.addDeckToDatabase(deck)
    }
}
