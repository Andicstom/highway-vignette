package com.example.vignette.feature.purchase.data.repository.model.mappers

import com.example.vignette.feature.purchase.data.api.model.VehicleProfileResponseData
import com.example.vignette.feature.purchase.data.repository.model.VehicleProfile

fun VehicleProfileResponseData.toDomain() = VehicleProfile(
    id = requestId,
    internationalRegistrationCode = internationalRegistrationCode,
    type = type,
    name = name,
    plate = plate,
    country = countryResponseData.hu,
    vignetteType = vignetteType,
)
