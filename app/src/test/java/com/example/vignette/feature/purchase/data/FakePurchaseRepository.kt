package com.example.vignette.feature.purchase.data

import com.example.vignette.feature.purchase.data.repository.PurchaseRepository
import com.example.vignette.feature.purchase.data.repository.model.Purchase
import com.example.vignette.feature.purchase.data.repository.model.PurchaseItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakePurchaseRepository : PurchaseRepository {

    private val _purchase = MutableStateFlow(Purchase())

    var orderResult = Result.success(Unit)

    override fun observePurchase(): Flow<Purchase> =
        _purchase.asStateFlow()


    override suspend fun addProduct(purchase: PurchaseItem) {
        _purchase.update {
            it.copy(
                purchaseItems = it.purchaseItems + purchase
            )
        }
    }

    override suspend fun removeProduct(purchase: PurchaseItem) {
        _purchase.update {
            it.copy(
                purchaseItems = it.purchaseItems - purchase
            )
        }
    }

    override suspend fun setPlate(plate: String) {
        _purchase.update {
            it.copy(
                plate = plate
            )
        }
    }

    override suspend fun removeAllProducts() {
        _purchase.update {
            it.copy(
                purchaseItems = emptyList()
            )
        }
    }

    override suspend fun orderVignette(vignetteProducts: List<PurchaseItem>): Result<Unit> {
        return orderResult
    }
}
