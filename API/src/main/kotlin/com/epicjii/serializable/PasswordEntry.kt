package com.epicjii.serializable

import kotlinx.serialization.Serializable

@Serializable
data class PasswordEntry(
    val domain: String,
    val key: String,
    val userName: String,
    val password: String
)