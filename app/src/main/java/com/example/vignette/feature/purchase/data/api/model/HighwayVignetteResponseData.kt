package com.example.vignette.feature.purchase.data.api.model

import com.google.gson.annotations.SerializedName

data class HighwayVignetteResponseData(
    @SerializedName("vignetteType") val vignetteType: List<String>,
    @SerializedName("vehicleCategory") val vehicleCategory: String,
    @SerializedName("cost") val cost: Long,
    @SerializedName("trxFee") val trxFee: Long,
    @SerializedName("sum") val sum: Long,
)
