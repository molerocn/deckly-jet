package com.molerocn.deckly.data.repository

import com.molerocn.deckly.data.database.dao.CardDao
import com.molerocn.deckly.data.database.entities.toEntityModel
import com.molerocn.deckly.data.network.service.CardService
import com.molerocn.deckly.domain.model.CardModel
import com.molerocn.deckly.domain.model.toDomainModel
import javax.inject.Inject

class CardRepository @Inject constructor(
    private val api: CardService,
    private val cardDao: CardDao
) {

    suspend fun getCardsByDeckFromApi(deckId: Int): List<CardModel> {
        val response = api.getCardsByDeck(deckId)
        return response.map { it.toDomainModel() }
    }

    suspend fun getCardsByDeckFromDatabase(deckId: Int): List<CardModel> {
        val response = cardDao.getCardsByDeck(deckId)
        return response.map { it.toDomainModel() }
    }

    suspend fun addCardToDatabase(card: CardModel): CardModel {
        val id = cardDao.insertCard(card.toEntityModel())
        return card.copy(id = id.toInt())
    }

    suspend fun clearCards() {
        cardDao.deleteAllCards()
    }

    suspend fun amountOfDueCardsByDeck(deckId: Int): Int {
        return cardDao.amountOfDueCardsByDeck(deckId)
    }

    suspend fun isThereCardWithFrontInDeck(deckId: Int, front: String): Boolean {
        return cardDao.isThereCardWithFrontInDeck(deckId, front)
    }

    suspend fun updateCard(card: CardModel) {
        cardDao.updateCard(card.toEntityModel())
    }

    suspend fun deleteCards(vararg cards: CardModel) {
        for (card in cards) {
            cardDao.deleteCards(card.toEntityModel())
        }
    }
}