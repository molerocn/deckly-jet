package com.molerocn.deckly.domain.usecase

import com.molerocn.deckly.data.repository.ReviewLogRepository
import com.molerocn.deckly.domain.model.ReviewLogModel
import javax.inject.Inject

class InsertLogUseCase @Inject constructor(
    private val repository: ReviewLogRepository
) {

    suspend operator fun invoke(log: ReviewLogModel): ReviewLogModel {
        return repository.insertLog(log)
    }
    
}