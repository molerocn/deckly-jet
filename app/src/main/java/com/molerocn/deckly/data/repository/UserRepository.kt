package com.molerocn.deckly.data.repository

import com.molerocn.deckly.core.AuthHelper
import com.molerocn.deckly.domain.model.User
import com.molerocn.deckly.data.network.service.UserService
import com.molerocn.deckly.data.preferences.DataStoreManager
import com.molerocn.deckly.domain.model.toDomainModel
import jakarta.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserService,
    private val dataStoreManager: DataStoreManager
) {

    suspend fun signInWithGoogle(token: String): User? {
        val response = api.signInWithGoogle(token)
        response?.let {
            dataStoreManager.saveUserData(
                token = it.accessToken,
                name = it.name.substringBefore(" "),
                email = it.email
            )
        }
        return response?.toDomainModel()
    }

    suspend fun getUserToken(): String {
        return dataStoreManager.getToken()
    }

    suspend fun getUserName(): String {
        return dataStoreManager.getUserName()
    }

    suspend fun getUserEmail(): String {
        return dataStoreManager.getUserEmail()
    }

    suspend fun clearUserSession() {
        dataStoreManager.clearUserData()
    }
}