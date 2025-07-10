package com.molerocn.deckly.data.network.api_client

import com.molerocn.deckly.data.network.model.CardModelApi
import retrofit2.Response
import retrofit2.http.GET

interface CardApiClient {
    @GET("/cards")
    suspend fun getAllCards(): Response<List<CardModelApi>>
}