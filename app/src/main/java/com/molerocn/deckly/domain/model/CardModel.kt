package com.molerocn.deckly.domain.model

import com.molerocn.deckly.data.database.entities.CardEntity

enum class CardStatus {
    New,
    Learning,
    Review,
    Relearning
}

data class CardModel(
    val id: Int = 0,
    val deckId: Int,
    val front: String,
    val back: String,
    val status: CardStatus = CardStatus.New,
    val due: Long = System.currentTimeMillis(),
    val lastReview: Long? = null,
    val reps: Int = 0,
    val lapses: Int = 0
)

fun CardEntity.toDomainModel() = CardModel(
    id = id,
    deckId = deckId,
    front = front,
    back = back,
    status = CardStatus.valueOf(status),
    due = due,
    lastReview = lastReview,
    reps = reps,
    lapses = lapses
)
