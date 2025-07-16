package com.molerocn.deckly.domain.model

import com.molerocn.deckly.core.FSRSCard
import com.molerocn.deckly.core.Rating
import com.molerocn.deckly.data.database.entities.CardEntity
import com.molerocn.deckly.data.network.model.CardModelApi

enum class CardStatus {
    New,
    Learning,
    Review,
    Relearning
}

data class CardModel(
    val id: Int = 0,
    val deckId: Int,
    val back: String,
    val front: String,
    val status: CardStatus = CardStatus.New,
    val due: Long = System.currentTimeMillis(),
    val lastReview: Long? = null,
    val reps: Int = 0,
    val lapses: Int = 0,
    val stability:Double = 0.0,
    val difficulty:Double = 0.0
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
    lapses = lapses,
    stability = stability,
    difficulty = difficulty
)

fun CardModelApi.toDomainModel() = CardModel(
    id = id,
    front = front,
    back = back,
    deckId = deckId
)