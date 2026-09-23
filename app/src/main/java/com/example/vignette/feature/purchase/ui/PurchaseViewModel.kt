package com.example.vignette.feature.purchase.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vignette.core.ui.UiEvent
import com.example.vignette.feature.purchase.data.repository.PurchaseRepository
import com.example.vignette.feature.purchase.data.repository.model.PurchaseItem
import com.example.vignette.feature.purchase.data.repository.model.VignetteTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class PurchaseItemUiData(
    val name: String,
    val price: Long,
)

fun PurchaseItem.toUiData(): PurchaseItemUiData =
    PurchaseItemUiData(
        name = category,
        price = cost,
    )

data class PurchaseUiState(
    val orderItems: List<PurchaseItemUiData> = emptyList(),
    val transactionFee: Long = 0,
    val totalPrice: Long = 0,
    val plate: String = "",
    val type: String = "",
)

@HiltViewModel
class PurchaseViewModel @Inject constructor(
    private val purchaseRepository: PurchaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PurchaseUiState())
    val uiState: StateFlow<PurchaseUiState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<UiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        observePurchase()
    }

    private fun observePurchase() {
        viewModelScope.launch {
            purchaseRepository.observePurchase().collect { purchase ->
                _uiState.update { uiState ->
                    uiState.copy(
                        totalPrice = purchase.totalPrice,
                        transactionFee = purchase.transactionFee,
                        orderItems = purchase.purchaseItems.map { purchaseItem ->
                            purchaseItem.toUiData()
                        },
                        plate = purchase.plate ?: "",
                        type = getVignetteTypeText(purchase.purchaseItems.firstOrNull()?.type),
                    )
                }
            }
        }
    }

    fun purchaseProduct() {
        viewModelScope.launch {
            val purchaseItems = purchaseRepository.observePurchase().first().purchaseItems

            purchaseRepository.orderVignette(
                vignetteProducts = purchaseItems.map { purchaseItem ->
                    PurchaseItem(
                        type = purchaseItem.type,
                        category = purchaseItem.category,
                        cost = purchaseItem.cost,
                        transactionFee = purchaseItem.transactionFee,
                        total = purchaseItem.total,
                    )
                }
            ).onSuccess {
                purchaseRepository.removeAllProducts()
                _uiEvents.send(UiEvent.Navigate)
            }.onFailure {
                _uiEvents.send(UiEvent.ShowErrorToast)
            }
        }
    }

    private fun getVignetteTypeText(vignetteType: VignetteTypes?): String =
        when (vignetteType) {
            VignetteTypes.DAY -> "Napi országos"
            VignetteTypes.WEEK -> "Heti országos"
            VignetteTypes.MONTH -> "Havi országos"
            VignetteTypes.YEAR -> "Éves országos"
            VignetteTypes.COUNTY -> "Éves vármegyei"
            else -> ""
        }
}
