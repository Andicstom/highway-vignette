package com.example.vignette.feature.purchase.data.repository.model

data class VignetteProduct(
    val typeId: String,
    val vignetteType: VignetteTypes,
    val name: String = "",
    val vehicleCategory: String, // D1
    val category: String = "", // CAR
    val cost: Long,
    val transactionFee: Long,
    val total: Long,
)

enum class VignetteTypes {
    DAY,
    WEEK,
    MONTH,
    YEAR,
    COUNTY,
}
