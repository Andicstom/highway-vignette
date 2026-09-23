package com.example.vignette.feature.purchase.data.api.model

import com.google.gson.annotations.SerializedName

data class CountryResponseData(
    @SerializedName("hu") val hu: String,
    @SerializedName("en") val en: String,
)
