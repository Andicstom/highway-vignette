package com.example.vignette.feature.purchase.data.repository

import com.example.vignette.di.IODispatcher
import com.example.vignette.feature.purchase.data.api.ApiException
import com.example.vignette.feature.purchase.data.api.VignetteNetworkApi
import com.example.vignette.feature.purchase.data.repository.model.Purchase
import com.example.vignette.feature.purchase.data.repository.model.PurchaseItem
import com.example.vignette.feature.purchase.data.repository.model.mappers.toVignetteOrderRequestData
import jakarta.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

private const val ERROR_CODE = "ERROR"

class DefaultPurchaseRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val vignetteNetworkApi: VignetteNetworkApi,
) : PurchaseRepository {
    private val _purchases = MutableStateFlow(Purchase())

    override fun observePurchase(): Flow<Purchase> =
        _purchases.asStateFlow()

    override suspend fun addProduct(purchase: PurchaseItem) {
        _purchases.update {
            it.copy(
                purchaseItems = it.purchaseItems + purchase
            )
        }

        updatePrices()
    }

    override suspend fun removeProduct(purchase: PurchaseItem) {
        _purchases.update {
            it.copy(
                purchaseItems = it.purchaseItems - purchase
            )
        }

        updatePrices()
    }

    override suspend fun setPlate(plate: String) {
        _purchases.update {
            it.copy(
                plate = plate
            )
        }
    }

    override suspend fun removeAllProducts() {
        _purchases.update {
            it.copy(
                purchaseItems = emptyList()
            )
        }

        updatePrices()
    }

    private fun updatePrices() {
        updateTransactionFee()
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        _purchases.update { purchase ->
            purchase.copy(
                totalPrice = purchase.purchaseItems.sumOf { it.cost } + purchase.transactionFee
            )
        }
    }

    private fun updateTransactionFee() {
        _purchases.update { purchase ->
            purchase.copy(
                transactionFee = purchase.purchaseItems.sumOf { it.transactionFee }
            )
        }
    }

    override suspend fun orderVignette(
        vignetteProducts: List<PurchaseItem>,
    ): Result<Unit> = withContext(ioDispatcher) {
        try {
            val response = vignetteNetworkApi.postHighwayVignetteOrder(
                highwayOrders = vignetteProducts.toVignetteOrderRequestData()
            )

            if (response.statusCode?.equals(ERROR_CODE, ignoreCase = true) == true) {
                throw ApiException.InvalidMissingOrderException()
            }

            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }
}
