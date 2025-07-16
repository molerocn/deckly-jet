package com.molerocn.deckly.data.network.api_client

import com.molerocn.deckly.data.network.model.CardModelApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface CardApiClient {
    @GET("/cards")
    suspend fun getCardsByDeck(@Body body: Map<String, Any>): Response<List<CardModelApi>>

    @GET("/sync")
    suspend fun sync(@Body body: Map<String, Any>)
}