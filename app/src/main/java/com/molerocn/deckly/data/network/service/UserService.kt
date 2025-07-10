package com.molerocn.deckly.data.network.service

import com.molerocn.deckly.data.network.api_client.UserApiClient
import com.molerocn.deckly.data.network.model.UserModelApi
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserService @Inject constructor(
    private val api: UserApiClient
) {

    suspend fun signInWithGoogle(token: String): UserModelApi? {
        return withContext(Dispatchers.IO) {
            val body = mapOf("token" to token)
            val response = api.signInWithGoogle(body)
            response.body()
        }
    }
}