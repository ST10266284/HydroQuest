package com.hydroquest.app.model

data class UserSettingsRequest(
    val dailyGoal: Int,
    val units: String,
    val theme: String
)
