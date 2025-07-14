package com.molerocn.deckly.domain.usecase

import com.molerocn.deckly.data.repository.CardRepository
import com.molerocn.deckly.domain.model.CardModel
import javax.inject.Inject

class AddCardUseCase @Inject constructor(private val repository: CardRepository) {

    // TODO: agregar logica para decidir si obtener los datos desde api o local database
    suspend operator fun invoke(card: CardModel): CardModel? {
        return repository.addCardToDatabase(card)
    }
}
