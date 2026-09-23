package com.example.vignette.di

import com.example.vignette.feature.purchase.data.repository.DefaultPurchaseRepository
import com.example.vignette.feature.purchase.data.repository.DefaultVignetteRepository
import com.example.vignette.feature.purchase.data.repository.PurchaseRepository
import com.example.vignette.feature.purchase.data.repository.VignetteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindVignetteRepository(
        defaultVignetteRepository: DefaultVignetteRepository
    ): VignetteRepository

    @Binds
    @Singleton
    fun bindPurchaseRepository(
        defaultPurchaseRepository: DefaultPurchaseRepository
    ): PurchaseRepository
}
