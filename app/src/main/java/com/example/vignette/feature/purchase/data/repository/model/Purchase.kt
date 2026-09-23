package com.example.vignette.feature.purchase.data.repository.model

data class Purchase(
    val plate: String? = null,
    val purchaseItems: List<PurchaseItem> = emptyList(),
    val transactionFee: Long = 0,
    val totalPrice: Long = 0,
)

data class PurchaseItem(
    val type: VignetteTypes,
    val category: String,
    val cost: Long = 0,
    val total: Long = 0,
    val transactionFee: Long = 0,
)
