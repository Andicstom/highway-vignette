package com.example.vignette.feature.purchase.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class VignetteOrderItemRequestData (
    val type: String,
    val category: String,
    val cost: Long,
)
