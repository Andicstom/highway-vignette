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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VehicleProfileUiData(
    val category: String,
    val registration: String,
    val ownerName: String,
)

data class VignetteOptionUiData(
    val type: VignetteTypes,
    val title: String,
    val vehicleCategory: String,
    val category: String,
    val transactionFee: Long,
    val price: Long,
)

data class VignetteScreenUiState(
    val vignetteOptionUiData: List<VignetteOptionUiData> = emptyList(),
    val selectedVignetteOptionUiData: VignetteOptionUiData? = null,
    val vehicleProfileUiData: VehicleProfileUiData? = null,
)

@HiltViewModel
class VignetteViewModel @Inject constructor(
    private val vignetteRepository: VignetteRepository,
    private val purchaseRepository: PurchaseRepository,
) : ViewModel() {
    private val _uiSate = MutableStateFlow(VignetteScreenUiState())
    val uiState: StateFlow<VignetteScreenUiState> = _uiSate.asStateFlow()

    private val _uiEvents = Channel<UiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        loadData()
    }

    private fun loadData() {
        loadVehicleProfile()
        loadVignetteProducts()
    }

    private fun loadVehicleProfile() {
        viewModelScope.launch {
            vignetteRepository.getVehicleProfile().onSuccess { vehicleProfile ->
                onVehicleProfileLoaded(
                    VehicleProfileUiData(
                        category = vehicleProfile.vignetteType,
                        registration = vehicleProfile.plate,
                        ownerName = vehicleProfile.name,
                    )
                )
                purchaseRepository.setPlate(vehicleProfile.plate)
            }.onFailure {
                _uiEvents.send(UiEvent.ShowErrorToast)
            }
        }
    }

    private fun loadVignetteProducts() {
        viewModelScope.launch {
            vignetteRepository.getCountryVignetteProducts().onSuccess { vignetteProducts ->
                onVignetteProductsLoaded(vignetteProducts)
            }.onFailure {
                _uiEvents.send(UiEvent.ShowErrorToast)
            }
        }
    }

    private fun onVehicleProfileLoaded(vehicleProfileUiData: VehicleProfileUiData) {
        _uiSate.update {
            it.copy(
                vehicleProfileUiData = vehicleProfileUiData,
            )
        }
    }

    private fun onVignetteProductsLoaded(vignetteProducts: List<VignetteProduct>) {
        val vignetteOptionUiData = vignetteProducts.map { vignetteProduct ->
            VignetteOptionUiData(
                type = vignetteProduct.vignetteType,
                title = getVignetteTitle(
                    vignetteProduct.vignetteType,
                    vignetteProduct.category
                ),
                vehicleCategory = vignetteProduct.vehicleCategory,
                price = vignetteProduct.cost,
                transactionFee = vignetteProduct.transactionFee,
                category = vignetteProduct.category,
            )
        }

        _uiSate.update {
            it.copy(
                vignetteOptionUiData = vignetteOptionUiData,
            )
        }
    }

    private fun getVignetteTitle(vignetteType: VignetteTypes, category: String): String =
        when (vignetteType) {
            VignetteTypes.DAY -> "$category - napi (1 napos)"
            VignetteTypes.WEEK -> "$category - heti (10 napos)"
            VignetteTypes.MONTH -> "$category - havi"
            VignetteTypes.YEAR -> "$category - éves"
            VignetteTypes.COUNTY -> "$category - vármegye"
        }

    fun onVignetteOptionSelected(vignetteOptionUiData: VignetteOptionUiData) {
        _uiSate.update {
            it.copy(
                selectedVignetteOptionUiData = vignetteOptionUiData,
            )
        }

        setPurchaseItem(vignetteOptionUiData)
    }

    fun resetSelectedVignetteOption() {
        _uiSate.update {
            it.copy(
                selectedVignetteOptionUiData = null,
            )
        }

        viewModelScope.launch {
            purchaseRepository.removeAllProducts()
        }
    }

    private fun setPurchaseItem(vignetteOptionUiData: VignetteOptionUiData) {
        viewModelScope.launch {
            purchaseRepository.removeAllProducts()

            purchaseRepository.addProduct(
                PurchaseItem(
                    type = vignetteOptionUiData.type,
                    category = vignetteOptionUiData.vehicleCategory,
                    cost = vignetteOptionUiData.price,
                    transactionFee = vignetteOptionUiData.transactionFee,
                )
            )
        }
    }
}
