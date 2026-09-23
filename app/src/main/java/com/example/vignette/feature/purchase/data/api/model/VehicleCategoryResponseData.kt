package com.example.vignette.feature.purchase.data.api.model

import com.google.gson.annotations.SerializedName

data class VehicleCategoryResponseData(
    @SerializedName("category") val category: String,
    @SerializedName("vignetteCategory") val vignetteCategory: String,
    @SerializedName("name") val name: LocalizedNameResponseData,
)
