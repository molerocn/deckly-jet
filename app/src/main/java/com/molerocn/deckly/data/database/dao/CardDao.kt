package com.molerocn.deckly.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.molerocn.deckly.data.database.entities.CardEntity

// querys siguiendo la guia https://developer.android.com/training/data-storage/room/accessing-data
@Dao
interface CardDao {

    @Query("SELECT * FROM card_table WHERE deck_id = :deckId")
    suspend fun getCardsByDeck(deckId: Int): List<CardEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: CardEntity): Long

    @Delete
    suspend fun deleteCards(vararg cards: CardEntity)

    @Query("DELETE FROM card_table")
    suspend fun deleteAllCards()

    @Query("SELECT COUNT(*) FROM card_table WHERE due <= :now AND deck_id = :deckId")
    suspend fun amountOfDueCardsByDeck(deckId: Int, now: Long = System.currentTimeMillis()): Int
}