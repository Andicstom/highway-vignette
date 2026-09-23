package com.example.vignette.feature.purchase.data.api.model

import com.google.gson.annotations.SerializedName


data class HighwayInfoResponseData(
    @SerializedName("requestId") val requestId: String,
    @SerializedName("statusCode") val statusCode: String,
    @SerializedName("payload") val payload: HighwayPayloadResponseData,
)
