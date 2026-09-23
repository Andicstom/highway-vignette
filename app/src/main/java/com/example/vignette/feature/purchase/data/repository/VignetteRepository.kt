package com.example.vignette.feature.purchase.data.repository

import com.example.vignette.feature.purchase.data.repository.model.VehicleProfile
import com.example.vignette.feature.purchase.data.repository.model.VignetteProduct

interface VignetteRepository {

    suspend fun getCountyVignetteProducts(): Result<List<VignetteProduct>>

    suspend fun getCountryVignetteProducts(): Result<List<VignetteProduct>>

    suspend fun getVehicleProfile(): Result<VehicleProfile>
}
