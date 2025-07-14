package com.molerocn.deckly.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.molerocn.deckly.data.database.entities.DeckEntity

// querys siguiendo la guia https://developer.android.com/training/data-storage/room/accessing-data
@Dao
interface DeckDao {

    @Query("SELECT * FROM deck_table")
    suspend fun getDecks(): List<DeckEntity>

    @Query("SELECT * FROM deck_table WHERE id = :deckId")
    suspend fun getDeck(deckId: Int): DeckEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeck(deck: DeckEntity): Long

    @Delete
    suspend fun deleteDecks(vararg decks: DeckEntity)

    @Query("DELETE FROM deck_table")
    suspend fun deleteAllDecks()

    // metodo para verificar si existe algun deck con el mismo front
    @Query("SELECT COUNT(*) > 0 FROM deck_table WHERE name = :name")
    suspend fun deckExist(name: String): Boolean
}
