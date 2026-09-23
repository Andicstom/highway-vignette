package com.example.vignette.feature.purchase.ui

import com.example.vignette.core.ui.UiEvent
import com.example.vignette.feature.purchase.data.FakePurchaseRepository
import com.example.vignette.feature.purchase.data.FakeVignetteRepository
import com.example.vignette.feature.purchase.data.countryVignetteProduct1
import com.example.vignette.feature.purchase.data.repository.model.VignetteTypes
import com.example.vignette.feature.purchase.data.vehicleProfile1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

val vehicleProfileUiData1 = VehicleProfileUiData(
    category = "D1",
    registration = "abc-123",
    ownerName = "Michael Scott",
)

val vignetteOptionUiData1 = listOf(
    VignetteOptionUiData(
        type = VignetteTypes.DAY,
        title = "D1 - napi (1 napos)",
        vehicleCategory = "CAR",
        price = 6800,
        category = "D1",
        transactionFee = 200,
    ),
    VignetteOptionUiData(
        type = VignetteTypes.WEEK,
        title = "D1 - heti (10 napos)",
        vehicleCategory = "CAR",
        price = 6800,
        category = "D1",
        transactionFee = 200,
    )
)

@OptIn(ExperimentalCoroutinesApi::class)
class VignetteViewModelTest {

    private lateinit var fakeVignetteRepository: FakeVignetteRepository
    private lateinit var fakePurchaseRepository: FakePurchaseRepository
    private lateinit var vignetteViewModel: VignetteViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        fakeVignetteRepository = FakeVignetteRepository()
        fakePurchaseRepository = FakePurchaseRepository()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel() {
        vignetteViewModel = VignetteViewModel(
            vignetteRepository = fakeVignetteRepository,
            purchaseRepository = fakePurchaseRepository,
        )
    }

    @Test
    fun `when vehicle profile is loaded, state contains vehicle profile and plate is set`() =
        runTest {
            fakeVignetteRepository.vehicleProfileResult = Result.success(vehicleProfile1)

            createViewModel()
            advanceUntilIdle()

            Assert.assertEquals(
                vehicleProfileUiData1,
                vignetteViewModel.uiState.value.vehicleProfileUiData
            )
        }

    @Test
    fun `when vignette products are loaded, state contains vignette options`() = runTest {
        fakeVignetteRepository.vignetteProductsResult = Result.success(countryVignetteProduct1)

        createViewModel()
        advanceUntilIdle()

        Assert.assertEquals(
            vignetteOptionUiData1,
            vignetteViewModel.uiState.value.vignetteOptionUiData
        )
    }

    @Test
    fun `when vignette products loading fails, uiEvent contains error message`() = runTest {
        fakeVignetteRepository.vignetteProductsResult = Result.failure(Exception())

        createViewModel()
        advanceUntilIdle()

        Assert.assertEquals(
            UiEvent.ShowErrorToast,
            vignetteViewModel.uiEvents.first()
        )
    }

    @Test
    fun `when vehicle profile loading fails, uiEvent contains error message`() = runTest {
        fakeVignetteRepository.vehicleProfileResult = Result.failure(Exception())

        createViewModel()
        advanceUntilIdle()

        Assert.assertEquals(
            UiEvent.ShowErrorToast,
            vignetteViewModel.uiEvents.first()
        )
    }

    @Test
    fun `when vignette option is selected, state contains selected vignette option`() = runTest {
        createViewModel()
        advanceUntilIdle()

        vignetteViewModel.onVignetteOptionSelected(vignetteOptionUiData1[0])

        Assert.assertEquals(
            vignetteOptionUiData1[0],
            vignetteViewModel.uiState.value.selectedVignetteOptionUiData
        )
    }

    @Test
    fun `when vignette option is selected, purchase item is added`() = runTest {
        createViewModel()
        advanceUntilIdle()

        vignetteViewModel.onVignetteOptionSelected(vignetteOptionUiData1[0])
        advanceUntilIdle()

        Assert.assertEquals(
            1,
            fakePurchaseRepository.observePurchase().first().purchaseItems.size
        )

        Assert.assertEquals(
            vignetteOptionUiData1[0].price,
            fakePurchaseRepository.observePurchase().first().purchaseItems[0].cost
        )
    }

    @Test
    fun `when a new vignette option is selected, the old one was removed from the purchase`() =
        runTest {
            createViewModel()
            advanceUntilIdle()

            vignetteViewModel.onVignetteOptionSelected(vignetteOptionUiData1[0])
            advanceUntilIdle()

            Assert.assertEquals(
                1,
                fakePurchaseRepository.observePurchase().first().purchaseItems.size
            )

            vignetteViewModel.onVignetteOptionSelected(vignetteOptionUiData1[1])
            advanceUntilIdle()

            Assert.assertEquals(
                1,
                fakePurchaseRepository.observePurchase().first().purchaseItems.size
            )
        }
}
