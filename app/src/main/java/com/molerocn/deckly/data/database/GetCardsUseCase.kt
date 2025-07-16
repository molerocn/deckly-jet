package com.molerocn.deckly.data.database

import com.molerocn.deckly.data.repository.CardRepository
import com.molerocn.deckly.domain.model.CardModel
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(private val repository: CardRepository) {

    // TODO: agregar logica para decidir si obtener los datos desde api o local database
    suspend operator fun invoke(deckId: Int): List<CardModel> {
        return repository.getCardsByDeckFromDatabase(deckId)
    }
}
