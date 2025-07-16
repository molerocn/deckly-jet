package com.molerocn.deckly.data.repository

import com.molerocn.deckly.core.ReviewLog
import com.molerocn.deckly.data.database.dao.ReviewLogDao
import com.molerocn.deckly.data.database.entities.ReviewLogEntity
import com.molerocn.deckly.data.database.entities.toEntityModel
import com.molerocn.deckly.data.network.service.CardService
import com.molerocn.deckly.domain.model.CardModel
import com.molerocn.deckly.domain.model.ReviewLogModel
import com.molerocn.deckly.domain.model.toDomainModel
import javax.inject.Inject

class ReviewLogRepository @Inject constructor(
    private val dao: ReviewLogDao
) {

    suspend fun insertLog(log: ReviewLogModel): ReviewLogModel {
        val id = dao.insertLog(log.toEntityModel())
        return log.copy(id = id.toInt())
    }

    // suspend fun getCardsByDeckFromDatabase(deckId: Int): List<CardModel> {
    //     val response = reviewLogDao.getCardsByDeck(deckId)
    //     return response.map { it.toDomainModel() }
    // }

}
