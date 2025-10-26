package com.hydroquest.app.model

data class UpdateSettingsRequest(
    val goal: Int,
    val units: String,
    val theme: String
)

