package com.molerocn.deckly.di

import com.molerocn.deckly.BuildConfig
import com.molerocn.deckly.data.network.api_client.CardApiClient
import com.molerocn.deckly.data.network.api_client.UserApiClient
import retrofit2.converter.gson.GsonConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BACKEND_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideUserApiClient(retrofit: Retrofit): UserApiClient {
        return retrofit.create(UserApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideCardApiClient(retrofit: Retrofit): CardApiClient {
        return retrofit.create(CardApiClient::class.java)
    }

}