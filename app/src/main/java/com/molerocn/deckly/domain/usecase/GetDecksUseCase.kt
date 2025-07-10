package com.molerocn.deckly.domain.usecase

import com.molerocn.deckly.data.repository.DeckRepository
import com.molerocn.deckly.domain.model.Deck
import javax.inject.Inject

class GetDecksUseCase @Inject constructor(private val repository: DeckRepository) {

    // TODO: agregar logica para decidir si obtener los datos desde api o local database
    suspend operator fun invoke(): List<Deck> {
        return repository.getAllDecksFromDatabase()
    }
}