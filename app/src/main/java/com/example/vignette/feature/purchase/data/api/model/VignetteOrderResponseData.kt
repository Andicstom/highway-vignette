package com.example.vignette.feature.purchase.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class VignetteOrderResponseData(
    val statusCode: String? = null,
    val vignetteOrders: List<VignetteOrderItemRequestData>,
)
