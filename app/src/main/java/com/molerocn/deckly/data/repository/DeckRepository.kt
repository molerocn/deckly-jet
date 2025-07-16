package com.molerocn.deckly.data.repository

import com.molerocn.deckly.data.database.dao.DeckDao
import com.molerocn.deckly.data.database.entities.toEntityModel
import com.molerocn.deckly.domain.model.Deck
import com.molerocn.deckly.domain.model.toDomainModel
import javax.inject.Inject

class DeckRepository @Inject constructor(
    private val deckDao: DeckDao
) {

    suspend fun getAllDecksFromDatabase(): List<Deck> {
        val response = deckDao.getDecks()
        return response.map { it.toDomainModel() }
    }

    suspend fun getDeckFromDatabase(deckId: Int): Deck {
        val response = deckDao.getDeck(deckId)
        return response.toDomainModel()
    }

    suspend fun addDeckToDatabase(deck: Deck): Deck {
        val id = deckDao.insertDeck(deck.toEntityModel())
        return deck.copy(id = id.toInt())
    }

    suspend fun deckExistWithSameName(name: String): Boolean {
        return deckDao.deckExist(name)
    }

    suspend fun updateDeck(deck: Deck) {
        deckDao.updateDeck(deck.toEntityModel())
    }

    suspend fun deleteDeckFromDatabase(vararg decks: Deck) {
        for (deck in decks) {
            deckDao.deleteDecks(deck.toEntityModel())
        }
    }
}