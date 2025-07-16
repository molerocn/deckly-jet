package com.molerocn.deckly.domain.usecase

import android.util.Log
import com.molerocn.deckly.data.repository.CardRepository
import com.molerocn.deckly.domain.model.CardModel
import javax.inject.Inject

class UpdateCardUseCase @Inject constructor(
    private val repository: CardRepository
) {

    suspend operator fun invoke(card: CardModel, withFrontValidation: Boolean): Boolean {
        if (withFrontValidation && repository.isThereCardWithFrontInDeck(card.deckId, card.front)) {
            Log.i("", "Ya existe un cardo con  ese front")
            return false
        }
        repository.updateCard(card)
        return true
    }
}