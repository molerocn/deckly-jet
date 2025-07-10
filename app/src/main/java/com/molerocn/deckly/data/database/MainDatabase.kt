package com.molerocn.deckly.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.molerocn.deckly.data.database.dao.CardDao
import com.molerocn.deckly.data.database.dao.DeckDao
import com.molerocn.deckly.data.database.entities.CardEntity
import com.molerocn.deckly.data.database.entities.DeckEntity

@Database(entities = [CardEntity::class, DeckEntity::class], version = 1)
abstract class MainDatabase: RoomDatabase() {

    abstract fun getCardDao(): CardDao
    abstract fun getDeckDao(): DeckDao

}