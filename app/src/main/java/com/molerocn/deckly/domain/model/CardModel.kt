package com.molerocn.deckly.domain.model

import com.molerocn.deckly.data.database.entities.CardEntity

data class CardModel(
    var id: Int = 0,
    var deckId: Int,
    val back: String,
    val front: String
)

fun CardEntity.toDomainModel() = CardModel(
    deckId = deckId,
    back = back,
    front = front
)
