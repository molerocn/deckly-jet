package com.molerocn.deckly.domain.model

import com.molerocn.deckly.data.database.entities.CardEntity
import com.molerocn.deckly.data.network.model.CardModelApi

data class Card(
    var id: Int = 0,
    var deckId: Int,
    val back: String,
    val front: String
)

fun CardEntity.toDomainModel() = Card(
    deckId = deckId,
    back = back,
    front = front
)
