package com.molerocn.deckly.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.molerocn.deckly.domain.model.Deck

@Entity(tableName = "deck_table")
data class DeckEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String = "",
)


fun Deck.toEntityModel() = DeckEntity(
    name = name,
    description = description
)
