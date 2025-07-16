package com.molerocn.deckly.data.network.service

import com.molerocn.deckly.data.network.api_client.CardApiClient
import com.molerocn.deckly.data.network.model.CardModelApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CardService @Inject constructor(
    private val api: CardApiClient
) {

    suspend fun getCardsByDeck(deckId: Int): List<CardModelApi> {

        return withContext(Dispatchers.IO) {
            val body = mapOf("deckId" to deckId)
            val response = api.getCardsByDeck(body)
            response.body() ?: emptyList()
        }
    }
}