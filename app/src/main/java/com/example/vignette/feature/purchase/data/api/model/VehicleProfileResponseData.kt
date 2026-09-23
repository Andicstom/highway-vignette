package com.example.vignette.feature.purchase.data.api.model

import com.google.gson.annotations.SerializedName

data class VehicleProfileResponseData(
    @SerializedName("requestId") val requestId: String,
    @SerializedName("statusCode") val statusCode: String,
    @SerializedName("internationalRegistrationCode") val  internationalRegistrationCode: String,
    @SerializedName("type") val type: String,
    @SerializedName("name") val name: String,
    @SerializedName("plate") val plate: String,
    @SerializedName("country") val countryResponseData: CountryResponseData,
    @SerializedName("vignetteType") val vignetteType: String,
)
