package com.molerocn.deckly.domain.model

import com.molerocn.deckly.data.database.entities.ReviewLogEntity
import kotlin.Int

data class ReviewLogModel (
    val id: Int = 0,
    val cardId: Int,
    val rating: String,
    val elapsedDays: Double,
    val scheduledDays: Double,
    val reviewDate: String,
    val status: String
)

fun ReviewLogEntity.toDomainModel() = ReviewLogModel(
    id = id,
    cardId = cardId,
    rating = rating,
    elapsedDays = elapsedDays,
    scheduledDays = scheduledDays,
    reviewDate = reviewDate,
    status = status
)
