package com.example.vignette.feature.purchase.data.api

import com.example.vignette.feature.purchase.data.api.model.HighwayInfoResponseData
import com.example.vignette.feature.purchase.data.api.model.VehicleProfileResponseData
import com.example.vignette.feature.purchase.data.api.model.VignetteOrderRequestData
import com.example.vignette.feature.purchase.data.api.model.VignetteOrderResponseData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface VignetteNetworkApi {

    @GET("/v1/highway/info")
    suspend fun getVignetteInfo(): HighwayInfoResponseData

    @GET("/v1/highway/vehicle")
    suspend fun getVehicleProfile(): VehicleProfileResponseData

    @POST("v1/highway/order")
    suspend fun postHighwayVignetteOrder(
        @Body highwayOrders: VignetteOrderRequestData,
    ): VignetteOrderResponseData

}
