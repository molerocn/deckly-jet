package com.molerocn.deckly.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "review_logs",
    foreignKeys = [ForeignKey(
        entity = CardEntity::class,
        parentColumns = ["id"],
        childColumns = ["card_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("card_id")]
)

data class ReviewLogEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "card_id")
    val cardId: Int,

    @ColumnInfo(name = "rating")
    val rating: String, // "Again", "Hard", etc.

    @ColumnInfo(name = "elapsed_days")
    val elapsedDays: Double,

    @ColumnInfo(name = "scheduled_days")
    val scheduledDays: Double,

    @ColumnInfo(name = "review_date")
    val reviewDate: String, // ISO 8601 format: "YYYY-MM-DD HH:MM:SS"

    @ColumnInfo(name = "status")
    val status: String // "New", "Learning", etc.
)

