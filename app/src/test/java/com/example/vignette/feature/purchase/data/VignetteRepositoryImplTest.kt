package com.example.vignette.feature.purchase.data

import com.example.vignette.feature.purchase.data.api.model.HighwayInfoResponseData
import com.example.vignette.feature.purchase.data.api.model.HighwayPayloadResponseData
import com.example.vignette.feature.purchase.data.repository.DefaultVignetteRepository
import com.example.vignette.feature.purchase.data.repository.VignetteRepository
import junit.framework.TestCase
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

val highwayInfoResponse1 = HighwayInfoResponseData(
    requestId = "1234",
    statusCode = "OK",
    payload = HighwayPayloadResponseData(
        highwayVignettes = emptyList(),
        vehicleCategories = emptyList(),
        counties = emptyList(),
    )
)

class VignetteRepositoryImplTest {
    private lateinit var vignetteApi: FakeVignetteNetworkApi
    private lateinit var vignetteRepository: VignetteRepository
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        vignetteApi = FakeVignetteNetworkApi()
        vignetteRepository = DefaultVignetteRepository(
            vignetteApi,
            dispatcher,
        )
    }

    @Test
    fun `when api call fails, then return failure`() = runTest {
        val exception = IOException("Network unavailable")

        vignetteApi.vignetteInfoException = exception

        val result = vignetteRepository.getCountyVignetteProducts()

        TestCase.assertTrue(result.isFailure)
        TestCase.assertSame(exception, result.exceptionOrNull())
    }

    @Test
    fun `when api call is successful, then return success`() = runTest {
        val vignetteInfoResponse = highwayInfoResponse1

        vignetteApi.vignetteInfoResponse = vignetteInfoResponse

        val result = vignetteRepository.getCountyVignetteProducts()

        TestCase.assertTrue(result.isSuccess)
    }
}
