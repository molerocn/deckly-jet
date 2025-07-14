package com.molerocn.deckly.di

import android.content.Context
import androidx.room.Room
import com.molerocn.deckly.data.database.MainDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "deckly_db"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MainDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration(true)
            .build()

    @Singleton
    @Provides
    fun provideCardDao(db: MainDatabase) = db.getCardDao()

    @Singleton
    @Provides
    fun provideDeckDao(db: MainDatabase) = db.getDeckDao()

    @Singleton
    @Provides
    fun provideReviewLogDao(db: MainDatabase) = db.getReviewLogDao()
}