package com.molerocn.deckly.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.molerocn.deckly.data.database.entities.ReviewLogEntity

@Dao
interface ReviewLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: ReviewLogEntity)

    @Query("SELECT * FROM review_logs WHERE card_id = :cardId ORDER BY review_date DESC")
    suspend fun getLogsForCard(cardId: Int): List<ReviewLogEntity>

    @Query("SELECT * FROM review_logs ORDER BY review_date DESC LIMIT :limit")
    suspend fun getRecentLogs(limit: Int): List<ReviewLogEntity>

    @Query("DELETE FROM review_logs WHERE card_id = :cardId")
    suspend fun deleteLogsForCard(cardId: Int)

    @Query("DELETE FROM review_logs")
    suspend fun clearAllLogs()
}