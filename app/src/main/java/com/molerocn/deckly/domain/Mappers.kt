package com.molerocn.deckly.domain

import com.molerocn.deckly.core.FSRSCard
import com.molerocn.deckly.core.ReviewLog
import com.molerocn.deckly.core.Status
import com.molerocn.deckly.data.database.entities.ReviewLogEntity
import com.molerocn.deckly.domain.model.CardModel
import com.molerocn.deckly.domain.model.CardStatus
import java.util.Date

fun CardStatus.toFSRSStatus(): Status {
    return when (this) {
        CardStatus.New -> Status.New
        CardStatus.Learning -> Status.Learning
        CardStatus.Review -> Status.Review
        CardStatus.Relearning -> Status.Relearning
    }
}

fun Status.toCardStatus(): CardStatus {
    return when (this) {
        Status.New -> CardStatus.New
        Status.Learning -> CardStatus.Learning
        Status.Review -> CardStatus.Review
        Status.Relearning -> CardStatus.Relearning
    }
}

fun cardModelToFSRSCard(cardModel: CardModel): FSRSCard {
    return FSRSCard().apply {
        due = Date(cardModel.due)
        lastReview = cardModel.lastReview?.let { Date(it) } ?: Date()
        status = cardModel.status.toFSRSStatus()
        reps = cardModel.reps
        lapses = cardModel.lapses
        stability = cardModel.stability
        difficulty = cardModel.difficulty
    }
}

fun fsrsCardToCardModel(fsrsCard: FSRSCard, originalCardModel: CardModel): CardModel {
    return originalCardModel.copy(
        due = fsrsCard.due.time,
        lastReview = fsrsCard.lastReview.time,
        status = fsrsCard.status.toCardStatus(),
        reps = fsrsCard.reps,
        lapses = fsrsCard.lapses,
        stability = fsrsCard.stability,
        difficulty = fsrsCard.difficulty
    )
}