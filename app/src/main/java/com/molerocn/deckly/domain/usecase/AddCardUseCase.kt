package com.molerocn.deckly.domain.usecase

import com.molerocn.deckly.data.repository.CardRepository
import com.molerocn.deckly.data.repository.DeckRepository
import com.molerocn.deckly.domain.model.Card
import com.molerocn.deckly.domain.model.Deck
import javax.inject.Inject

class AddCardUseCase @Inject constructor(private val repository: CardRepository) {

    // TODO: agregar logica para decidir si obtener los datos desde api o local database
    suspend operator fun invoke(card: Card): Card? {
        return repository.addCardToDatabase(card)
    }
}
