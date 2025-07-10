package com.molerocn.deckly.data.repository

import com.molerocn.deckly.core.AuthHelper
import com.molerocn.deckly.domain.model.User
import com.molerocn.deckly.data.network.service.UserService
import com.molerocn.deckly.domain.model.toDomainModel
import jakarta.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserService,
    private val authHelper: AuthHelper
) {

    suspend fun signInWithGoogle(token: String): User? {
        val response = api.signInWithGoogle(token)
        response?.let {
            authHelper.signIn(mapOf(
                "name" to it.name.substringBefore(" "),
                "email" to it.email,
                "picture" to it.picture,
                "access_token" to it.accessToken
            ))
        }
        return response?.toDomainModel() 
    }
}