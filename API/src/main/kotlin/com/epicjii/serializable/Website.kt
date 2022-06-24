package com.epicjii.serializable

@kotlinx.serialization.Serializable
data class Website(
    val domain: String,
    val key: String? = null
)