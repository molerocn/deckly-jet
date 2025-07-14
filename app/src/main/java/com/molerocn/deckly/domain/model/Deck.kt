package com.molerocn.deckly.domain.model

import com.molerocn.deckly.data.database.entities.DeckEntity

data class Deck(
    var id: Int = 0,
    val name: String,
    val description: String = "",
    val amountOfCardsToByStudy: Int = 0
    // TODO: agregar los datos de la cantidad de tarjetas para estudiar
    // val numberToByStudy: Int
)

fun DeckEntity.toDomainModel() = Deck(
    id = id,
    name = name
)