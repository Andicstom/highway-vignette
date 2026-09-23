package com.example.vignette.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vignette.R
import com.example.vignette.core.ui.component.AppTopBar
import com.example.vignette.core.ui.component.AppTopBarConfig
import com.example.vignette.feature.purchase.ui.CountyScreen
import com.example.vignette.feature.purchase.ui.PurchaseScreen
import com.example.vignette.feature.purchase.ui.SuccessScreen
import com.example.vignette.feature.purchase.ui.VignetteScreen

sealed class Screen(val route: String) {
    data object VignetteScreen : Screen("VignetteScreen")
    data object CountyScreen : Screen("CountyScreen")
    data object PurchaseScreen : Screen("PurchaseScreen")
    data object SuccessScreen : Screen("SuccessScreen")
}

@Composable
fun App() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val topBarConfig = when (currentRoute) {
        Screen.VignetteScreen.route -> {
            AppTopBarConfig(
                title = stringResource(R.string.appbar_title),
                showBackButton = true,
            )
        }

        Screen.CountyScreen.route -> {
            AppTopBarConfig(
                title = stringResource(R.string.appbar_title),
                showBackButton = true,
            )
        }

        Screen.PurchaseScreen.route -> {
            AppTopBarConfig(
                title = stringResource(R.string.appbar_title),
                showBackButton = true,
            )
        }

        else -> null
    }

    val backgroundColor = when (currentRoute) {
        Screen.VignetteScreen.route -> MaterialTheme.colorScheme.surfaceDim
        Screen.CountyScreen.route -> MaterialTheme.colorScheme.background
        Screen.PurchaseScreen.route -> MaterialTheme.colorScheme.background
        Screen.SuccessScreen.route -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.background
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.statusBars,
        containerColor = backgroundColor,
        topBar = {
            if (topBarConfig != null) {
                AppTopBar(
                    config = topBarConfig,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.VignetteScreen.route
            ) {
                composable(Screen.VignetteScreen.route) {
                    VignetteScreen(
                        openCountyScreen = {
                            navController.navigate(Screen.CountyScreen.route)
                        },
                        openOrderScreen = {
                            navController.navigate(Screen.PurchaseScreen.route)
                        }
                    )
                }

                composable(Screen.CountyScreen.route) {
                    CountyScreen(
                        openOrderScreen = {
                            navController.navigate(Screen.PurchaseScreen.route)
                        }
                    )
                }

                composable(Screen.PurchaseScreen.route) {
                    PurchaseScreen(
                        onBack = {
                            navController.popBackStack()
                        },
                        openSuccessScreen = {
                            navController.navigate(Screen.SuccessScreen.route)
                        }
                    )
                }

                composable(Screen.SuccessScreen.route) {
                    SuccessScreen(
                        onBack = {
                            navController.popBackStack(
                                route = Screen.VignetteScreen.route,
                                inclusive = false,
                            )
                        }
                    )
                }
            }
        }
    }
}
