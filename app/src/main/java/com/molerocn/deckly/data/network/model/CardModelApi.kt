package com.molerocn.deckly.data.network.model

import com.google.gson.annotations.SerializedName

data class CardModelApi(
    @SerializedName("id") val id: Int,
    @SerializedName("deck_id") val deckId: Int,
    @SerializedName("front") val front: String,
    @SerializedName("back") val back: String
)