package com.example.vignette.feature.purchase.data.api.model

import com.google.gson.annotations.SerializedName

data class HighwayPayloadResponseData(
    @SerializedName("highwayVignettes") val highwayVignettes: List<HighwayVignetteResponseData>,
    @SerializedName("vehicleCategories") val vehicleCategories: List<VehicleCategoryResponseData>,
    @SerializedName("counties") val counties: List<CountyResponseData>,
)
