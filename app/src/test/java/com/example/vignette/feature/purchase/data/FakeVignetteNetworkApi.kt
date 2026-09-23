package com.example.vignette.feature.purchase.data

import com.example.vignette.feature.purchase.data.api.VignetteNetworkApi
import com.example.vignette.feature.purchase.data.api.model.HighwayInfoResponseData
import com.example.vignette.feature.purchase.data.api.model.VehicleProfileResponseData
import com.example.vignette.feature.purchase.data.api.model.VignetteOrderRequestData
import com.example.vignette.feature.purchase.data.api.model.VignetteOrderResponseData

class FakeVignetteNetworkApi : VignetteNetworkApi {

    var vignetteInfoResponse: HighwayInfoResponseData? = null
    var vehicleProfileResponse: VehicleProfileResponseData? = null
    var vignetteOrderResponse: VignetteOrderResponseData? = null

    var vignetteInfoException: Exception? = null
    var vehicleProfileException: Exception? = null
    var vignetteOrderException: Exception? = null

    override suspend fun getVignetteInfo(): HighwayInfoResponseData {
        vignetteInfoException?.let { throw it }

        return requireNotNull(vignetteInfoResponse)
    }

    override suspend fun getVehicleProfile(): VehicleProfileResponseData {
        vehicleProfileException?.let { throw it }

        return requireNotNull(vehicleProfileResponse)
    }

    override suspend fun postHighwayVignetteOrder(highwayOrders: VignetteOrderRequestData): VignetteOrderResponseData {
        vignetteOrderException?.let { throw it }

        return requireNotNull(vignetteOrderResponse)
    }
}
