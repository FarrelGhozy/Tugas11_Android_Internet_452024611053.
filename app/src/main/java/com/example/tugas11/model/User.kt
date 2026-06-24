package com.example.tugas11.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val login: String,
    val id: Int,
    @Json(name = "avatar_url")
    val avatarUrl: String
)
