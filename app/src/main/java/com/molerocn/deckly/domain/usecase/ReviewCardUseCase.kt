package com.molerocn.deckly.domain.usecase

import com.molerocn.deckly.core.FSRS
import com.molerocn.deckly.core.Rating
import com.molerocn.deckly.data.database.entities.toEntityModel
import com.molerocn.deckly.data.repository.CardRepository
import com.molerocn.deckly.domain.cardModelToFSRSCard
import com.molerocn.deckly.domain.fsrsCardToCardModel
import com.molerocn.deckly.domain.model.CardModel
import com.molerocn.deckly.domain.model.ReviewLogModel
import com.molerocn.deckly.presentation.screens.study_card.Difficult
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ReviewCardUseCase @Inject constructor(
    private val repository: CardRepository,
    private val insertReviewLogUseCase: InsertLogUseCase
) {
    private val fsrs = FSRS()

    suspend operator fun invoke( card: CardModel, difficulty: Difficult ): CardModel? {

        val fsrsCard = cardModelToFSRSCard(card)
        val now = Date()
        val result = fsrs.repeat(fsrsCard, now)

        val rating = when (difficulty) {
            Difficult.AGAIN -> Rating.Again
            Difficult.HARD -> Rating.Hard
            Difficult.GOOD -> Rating.Good
            Difficult.EASY -> Rating.Easy
        }

        val schedulingInfo = result[rating] ?: return null
        val updatedCard = fsrsCardToCardModel(schedulingInfo.card, card)

        repository.updateCard(updatedCard)

        val reviewLogModel = schedulingInfo.reviewLog.toReviewLogModel(card.id)
        insertReviewLogUseCase(reviewLogModel)

        return updatedCard
    }

    private fun com.molerocn.deckly.core.ReviewLog.toReviewLogModel(cardId: Int): ReviewLogModel {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return ReviewLogModel(
            cardId = cardId,
            rating = rating.name,
            elapsedDays = elapsedDays,
            scheduledDays = scheduledDays,
            reviewDate = dateFormat.format(review),
            status = status.name
        )
    }
}
