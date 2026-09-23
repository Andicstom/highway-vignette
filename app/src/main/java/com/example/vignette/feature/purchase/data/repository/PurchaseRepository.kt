package com.example.vignette.feature.purchase.data.repository

import com.example.vignette.feature.purchase.data.repository.model.Purchase
import com.example.vignette.feature.purchase.data.repository.model.PurchaseItem
import kotlinx.coroutines.flow.Flow

interface PurchaseRepository {
    fun observePurchase(): Flow<Purchase>

    suspend fun addProduct(purchase: PurchaseItem)

    suspend fun removeProduct(purchase: PurchaseItem)

    suspend fun setPlate(plate: String)

    suspend fun removeAllProducts()

    suspend fun orderVignette(vignetteProducts:List<PurchaseItem>): Result<Unit>
}
