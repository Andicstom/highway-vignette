package com.example.vignette.feature.purchase.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vignette.core.ui.UiEvent
import com.example.vignette.feature.purchase.data.repository.PurchaseRepository
import com.example.vignette.feature.purchase.data.repository.VignetteRepository
import com.example.vignette.feature.purchase.data.repository.model.PurchaseItem
import com.example.vignette.feature.purchase.data.repository.model.VignetteProduct
import com.example.vignette.feature.purchase.data.repository.model.VignetteTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CountyUiData(
    val id: String,
    val name: String,
    val price: Long,
    val transactionFee: Long,
)

fun VignetteProduct.toCountyUiData() = CountyUiData(
    id = typeId,
    name = name,
    price = cost,
    transactionFee = transactionFee,
)

fun List<VignetteProduct>.toCountyUiDataList() = map { it.toCountyUiData() }

data class CountyScreenUiState(
    val counties: List<CountyUiData> = emptyList(),
    val selectedCounties: List<CountyUiData> = emptyList(),
    val totalPriceText: Long = 0,
)

@HiltViewModel
class CountyViewModel @Inject constructor(
    private val vignetteRepository: VignetteRepository,
    private val purchaseRepository: PurchaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CountyScreenUiState())
    val uiState: StateFlow<CountyScreenUiState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<UiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        observePurchase()
        loadCounties()
    }

    private fun observePurchase() {
        viewModelScope.launch {
            purchaseRepository.observePurchase().collect { purchase ->
                _uiState.update {
                    it.copy(
                        totalPriceText = purchase.totalPrice,
                    )
                }
            }
        }
    }

    private fun loadCounties() {
        viewModelScope.launch {
            vignetteRepository.getCountyVignetteProducts().onSuccess { counties ->
                onCountiesLoaded(counties)
            }.onFailure {
                _uiEvents.send(UiEvent.ShowErrorToast)
            }
        }
    }

    private fun onCountiesLoaded(counties: List<VignetteProduct>) {
        _uiState.update { uiState ->
            uiState.copy(
                counties = counties.toCountyUiDataList(),
            )
        }
    }

    fun onCountyClick(county: CountyUiData) {
        val selected = _uiState.value.selectedCounties.contains(county)

        val newSelectedCounties = if (selected) {
            _uiState.value.selectedCounties - county
        } else {
            _uiState.value.selectedCounties + county
        }

        if (selected) {
            removePurchase(county)
        } else {
            addPurchase(county)
        }

        _uiState.update { uiState ->
            uiState.copy(
                selectedCounties = newSelectedCounties,
            )
        }
    }

    private fun addPurchase(county: CountyUiData) {
        viewModelScope.launch {
            purchaseRepository.addProduct(
                PurchaseItem(
                    type = VignetteTypes.COUNTY,
                    category = county.name,
                    cost = county.price,
                    transactionFee = county.transactionFee,
                    total = county.price,
                )
            )
        }
    }

    private fun removePurchase(county: CountyUiData) {
        viewModelScope.launch {
            purchaseRepository.removeProduct(
                PurchaseItem(
                    type = VignetteTypes.COUNTY,
                    category = county.name,
                    cost = county.price,
                    transactionFee = county.transactionFee,
                    total = county.price,
                )
            )
        }
    }
}
