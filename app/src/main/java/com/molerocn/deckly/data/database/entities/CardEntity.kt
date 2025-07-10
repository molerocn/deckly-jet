package com.molerocn.deckly.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.molerocn.deckly.domain.model.Card

@Entity(
    tableName = "card_table",
    foreignKeys = [ForeignKey(
        entity = DeckEntity::class,
        parentColumns = ["id"],
        childColumns = ["deck_id"]
    )]
)
data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,

    @ColumnInfo(name = "deck_id") val deckId: Int,
    @ColumnInfo(name = "front") val front: String,
    @ColumnInfo(name = "back") val back: String
)

fun Card.toEntityModel() = CardEntity(
    deckId = deckId,
    front = front,
    back = back
)