package com.molerocn.deckly.domain.usecase

import android.util.Log
import com.molerocn.deckly.data.repository.CardRepository
import com.molerocn.deckly.domain.model.CardModel
import javax.inject.Inject

class AddCardUseCase @Inject constructor(private val repository: CardRepository) {

    // TODO: agregar logica para decidir si obtener los datos desde api o local database
    suspend operator fun invoke(card: CardModel): CardModel? {
        if (repository.isThereCardWithFrontInDeck(card.deckId, card.front)) {
            return null
        }
        return repository.addCardToDatabase(card)
    }
}
