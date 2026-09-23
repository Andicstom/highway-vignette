package com.example.vignette.feature.purchase.data.repository.model.mappers

import com.example.vignette.feature.purchase.data.api.model.CountyResponseData
import com.example.vignette.feature.purchase.data.api.model.HighwayInfoResponseData
import com.example.vignette.feature.purchase.data.api.model.HighwayVignetteResponseData
import com.example.vignette.feature.purchase.data.api.model.LocalizedNameResponseData
import com.example.vignette.feature.purchase.data.api.model.VehicleCategoryResponseData
import com.example.vignette.feature.purchase.data.repository.model.County
import com.example.vignette.feature.purchase.data.repository.model.HighwayInfo
import com.example.vignette.feature.purchase.data.repository.model.LocalizedName
import com.example.vignette.feature.purchase.data.repository.model.VehicleCategory
import com.example.vignette.feature.purchase.data.repository.model.VignetteProduct
import com.example.vignette.feature.purchase.data.repository.model.VignetteTypes

fun HighwayVignetteResponseData.toDomain(): List<VignetteProduct> =
    vignetteType.map { vignette ->
        VignetteProduct(
            typeId = vignette,
            vignetteType = vignette.getVignetteType(),
            vehicleCategory = vehicleCategory,
            cost = cost,
            transactionFee = trxFee,
            total = sum,
        )
    }

fun String.getVignetteType(): VignetteTypes =
    when (this.lowercase()) {
        "day" -> VignetteTypes.DAY
        "week" -> VignetteTypes.WEEK
        "month" -> VignetteTypes.MONTH
        "year" -> VignetteTypes.YEAR
        else -> VignetteTypes.COUNTY
    }

fun VehicleCategoryResponseData.toDomain() = VehicleCategory(
    category = category,
    vignetteCategory = vignetteCategory,
    name = name.toDomain(),
)

fun LocalizedNameResponseData.toDomain() = LocalizedName(
    hu = hu,
    en = en,
)

fun CountyResponseData.toDomain() = County(
    id = id,
    name = name,
)

fun HighwayInfoResponseData.toDomain(): HighwayInfo {
    val vignetteProducts = payload.highwayVignettes.flatMap { it.toDomain() }.map { product ->
        val category = payload.vehicleCategories.find { it.category == product.vehicleCategory }

        when (product.vignetteType) {
            VignetteTypes.COUNTY -> product.copy(
                name = payload.counties.find { it.id == product.typeId }?.name ?: "",
                category = category?.vignetteCategory ?: "",
            )

            else -> product.copy(
                name = payload.vehicleCategories.find { it.category == product.vehicleCategory }
                    ?.name?.hu ?: "",
                category = category?.vignetteCategory ?: "",
            )
        }
    }

    return HighwayInfo(
        requestId = requestId,
        vignettes = vignetteProducts,
        vehicleCategories = payload.vehicleCategories.map { it.toDomain() },
        counties = payload.counties.map { it.toDomain() },
    )
}
