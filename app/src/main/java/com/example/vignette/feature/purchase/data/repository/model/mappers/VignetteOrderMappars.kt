package com.example.vignette.feature.purchase.data.repository.model.mappers

import com.example.vignette.feature.purchase.data.api.model.VignetteOrderItemRequestData
import com.example.vignette.feature.purchase.data.api.model.VignetteOrderRequestData
import com.example.vignette.feature.purchase.data.repository.model.PurchaseItem

fun PurchaseItem.toVignetteOrderItemRequestData(): VignetteOrderItemRequestData =
    VignetteOrderItemRequestData(
        type = type.name,
        category = category,
        cost = total,
    )

fun List<PurchaseItem>.toVignetteOrderItemRequestDataList(): List<VignetteOrderItemRequestData> =
    map { it.toVignetteOrderItemRequestData() }

fun List<PurchaseItem>.toVignetteOrderRequestData(): VignetteOrderRequestData =
    VignetteOrderRequestData(
        highwayOrders = toVignetteOrderItemRequestDataList(),
    )
