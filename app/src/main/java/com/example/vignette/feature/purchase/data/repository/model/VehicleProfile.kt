package com.example.vignette.feature.purchase.data.repository.model

data class VehicleProfile(
    val id: String,
    val internationalRegistrationCode: String,
    val type: String,
    val name: String,
    val plate: String,
    val country: String,
    val vignetteType: String,
)
