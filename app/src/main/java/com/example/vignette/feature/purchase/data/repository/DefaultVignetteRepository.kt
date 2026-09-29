package com.example.vignette.feature.purchase.data.repository

import com.example.vignette.di.IODispatcher
import com.example.vignette.feature.purchase.data.api.VignetteNetworkApi
import com.example.vignette.feature.purchase.data.repository.model.County
import com.example.vignette.feature.purchase.data.repository.model.VehicleProfile
import com.example.vignette.feature.purchase.data.repository.model.VignetteProduct
import com.example.vignette.feature.purchase.data.repository.model.VignetteTypes
import com.example.vignette.feature.purchase.data.repository.model.countyGraph
import com.example.vignette.feature.purchase.data.repository.model.mappers.toDomain
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultVignetteRepository @Inject constructor(
    private val vignetteNetworkApi: VignetteNetworkApi,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : VignetteRepository {

    override suspend fun getCountyVignetteProducts(): Result<List<VignetteProduct>> =
        withContext(ioDispatcher) {
            try {
                val products =
                    vignetteNetworkApi.getVignetteInfo().toDomain().vignettes.filter { vignette ->
                        vignette.vignetteType == VignetteTypes.COUNTY
                    }

                Result.success(products)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }

    override suspend fun getCountryVignetteProducts(): Result<List<VignetteProduct>> =
        withContext(ioDispatcher) {
            try {
                val products =
                    vignetteNetworkApi.getVignetteInfo().toDomain().vignettes.filter { vignette ->
                        vignette.vignetteType != VignetteTypes.COUNTY
                    }

                Result.success(products)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }

    override suspend fun getVehicleProfile(): Result<VehicleProfile> =
        withContext(ioDispatcher) {
            try {
                val response = vignetteNetworkApi.getVehicleProfile().toDomain()

                Result.success(response)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }

    override fun areCountiesNeighbours(counties: List<County>): Boolean {
        if (counties.size <= 1) return true

        val start = counties.first().id
        val visited = mutableSetOf<String>()
        val queue = ArrayDeque<String>()

        queue.add(start)

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()

            if (!visited.add(current)) continue

            countyGraph[current]
                .orEmpty()
                .filter { countyId ->
                    countyId in counties.map { county -> county.id } && countyId !in visited
                }
                .forEach(queue::add)
        }

        return visited.size == counties.size
    }
}
