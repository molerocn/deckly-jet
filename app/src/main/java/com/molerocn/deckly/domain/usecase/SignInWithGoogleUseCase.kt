package com.molerocn.deckly.domain.usecase

import com.molerocn.deckly.data.repository.UserRepository
import com.molerocn.deckly.domain.model.User
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(token: String): User? =
        repository.signInWithGoogle(token)
}
