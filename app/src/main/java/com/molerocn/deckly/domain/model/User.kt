package com.molerocn.deckly.domain.model

import com.molerocn.deckly.data.network.model.UserModelApi

data class User(
    val name: String
)

fun UserModelApi.toDomainModel(): User {
    return User(
        name = name.substringBefore(" ")
    )
}
