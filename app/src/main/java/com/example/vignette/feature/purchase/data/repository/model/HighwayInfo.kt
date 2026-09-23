package com.example.vignette.feature.purchase.data.repository.model

data class HighwayInfo(
    val requestId: String?,
    val vignettes: List<VignetteProduct>,
    val vehicleCategories: List<VehicleCategory>,
    val counties: List<County>,
)
