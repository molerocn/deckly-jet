package com.molerocn.deckly.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.molerocn.deckly.data.database.entities.CardEntity
import retrofit2.http.PATCH
import retrofit2.http.PUT

// querys siguiendo la guia https://developer.android.com/training/data-storage/room/accessing-data
@Dao
interface CardDao {

    @Query("SELECT * FROM card_table WHERE deck_id = :deckId AND due <= :now")
    suspend fun getCardsByDeck(
        deckId: Int,
        now: Long = System.currentTimeMillis()
    ): List<CardEntity>

    @Query("SELECT * FROM card_table WHERE id = :deckId")
    suspend fun getCard(deckId: Int): CardEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: CardEntity): Long

    @Update
    suspend fun updateCard(card: CardEntity)

    @Delete
    suspend fun deleteCards(vararg cards: CardEntity)

    @Query("DELETE FROM card_table")
    suspend fun deleteAllCards()

    @Query("SELECT COUNT(*) FROM card_table WHERE due <= :now AND deck_id = :deckId")
    suspend fun amountOfDueCardsByDeck(deckId: Int, now: Long = System.currentTimeMillis()): Int

    @Query("SELECT EXISTS(SELECT 1 FROM card_table WHERE deck_id = :deckId AND front = :front)")
    suspend fun isThereCardWithFrontInDeck(deckId: Int, front: String): Boolean

}