package com.molerocn.deckly.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.molerocn.deckly.domain.model.CardModel
import java.util.Date

@Entity(
    tableName = "card_table",
    foreignKeys = [ForeignKey(
        entity = DeckEntity::class,
        parentColumns = ["id"],
        childColumns = ["deck_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["deck_id"])]
)
data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,

    @ColumnInfo(name = "deck_id") val deckId: Int,
    @ColumnInfo(name = "front") val front: String,
    @ColumnInfo(name = "back") val back: String,

    @ColumnInfo(name = "status") val status: String = "New", // New, Learning, Review, Relearning

    @ColumnInfo(name = "due") val due: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "last_review") val lastReview: Long? = null,

    @ColumnInfo(name = "stability") val stability: Double = 0.0,
    @ColumnInfo(name = "difficulty") val difficulty: Double = 0.0,
    @ColumnInfo(name = "scheduled_days") val scheduledDays: Double = 0.0,
    @ColumnInfo(name = "elapsed_days") val elapsedDays: Double = 0.0,

    @ColumnInfo(name = "reps") val reps: Int = 0,
    @ColumnInfo(name = "lapses") val lapses: Int = 0
)

fun CardModel.toEntityModel() = CardEntity(
    deckId = deckId,
    front = front,
    back = back
)