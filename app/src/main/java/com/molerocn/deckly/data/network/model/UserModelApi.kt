package com.molerocn.deckly.data.network.model

import com.google.gson.annotations.SerializedName

data class UserModelApi(
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("picture") val picture: String,
    @SerializedName("access_token") val accessToken: String
)