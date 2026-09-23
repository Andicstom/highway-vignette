package com.example.vignette.feature.purchase.data

import com.example.vignette.feature.purchase.data.repository.VignetteRepository
import com.example.vignette.feature.purchase.data.repository.model.VehicleProfile
import com.example.vignette.feature.purchase.data.repository.model.VignetteProduct
import com.example.vignette.feature.purchase.data.repository.model.VignetteTypes

val vehicleProfile1 = VehicleProfile(
    id = "1",
    internationalRegistrationCode = "H",
    type = "CAR",
    name = "Michael Scott",
    plate = "abc-123",
    country = "Magyarország",
    vignetteType = "D1",
)

val countyVignetteProduct1 = listOf(
    VignetteProduct(
        typeId = "YEAR_11",
        vignetteType = VignetteTypes.COUNTY,
        name = "Bács-Kiskun",
        vehicleCategory = "CAR",
        cost = 5350,
        transactionFee = 200,
        total = 5550,
    ),
    VignetteProduct(
        typeId = "YEAR_12",
        vignetteType = VignetteTypes.COUNTY,
        name = "Baranya",
        vehicleCategory = "CAR",
        cost = 5350,
        transactionFee = 200,
        total = 5550,
    )
)

val countryVignetteProduct1 = listOf(
    VignetteProduct(
        typeId = "DAY",
        vignetteType = VignetteTypes.DAY,
        name = "DAY",
        vehicleCategory = "CAR",
        category = "D1",
        cost = 6800,
        transactionFee = 200,
        total = 7000,
    ),
    VignetteProduct(
        typeId = "WEEK",
        vignetteType = VignetteTypes.WEEK,
        name = "WEEK",
        vehicleCategory = "CAR",
        category = "D1",
        cost = 6800,
        transactionFee = 200,
        total = 7000,
    )
)

class FakeVignetteRepository : VignetteRepository {

    var vignetteProductsResult: Result<List<VignetteProduct>> =
        Result.success(emptyList())

    var vehicleProfileResult: Result<VehicleProfile> =
        Result.success(vehicleProfile1)


    override suspend fun getCountyVignetteProducts(): Result<List<VignetteProduct>> {
        return vignetteProductsResult
    }

    override suspend fun getCountryVignetteProducts(): Result<List<VignetteProduct>> {
        return vignetteProductsResult
    }

    override suspend fun getVehicleProfile(): Result<VehicleProfile> {
        return vehicleProfileResult
    }
}
