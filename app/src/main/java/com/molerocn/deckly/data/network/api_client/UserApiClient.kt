package com.molerocn.deckly.data.network.api_client

import com.molerocn.deckly.data.network.model.UserModelApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiClient {
    
    @POST("/auth/google")
    suspend fun signInWithGoogle(@Body body: Map<String, String>): Response<UserModelApi>
}
