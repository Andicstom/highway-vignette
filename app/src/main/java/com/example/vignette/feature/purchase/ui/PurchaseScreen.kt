package com.example.vignette.feature.purchase.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.vignette.R
import com.example.vignette.core.ui.UiEvent
import com.example.vignette.core.ui.component.ButtonType
import com.example.vignette.core.ui.component.TotalPurchase
import com.example.vignette.core.ui.component.VignetteButton
import com.example.vignette.core.ui.component.VignetteTitle
import com.example.vignette.core.ui.theme.VignetteTheme
import com.example.vignette.core.util.toPriceText

@Composable
fun PurchaseScreen(
    viewModel: PurchaseViewModel = hiltViewModel(),
    onBack: () -> Unit,
    openSuccessScreen: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    val errorMessage = stringResource(R.string.error_message)

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                UiEvent.Navigate -> openSuccessScreen()
                is UiEvent.ShowErrorToast -> {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    PurchaseScreenContent(
        onOrderClick = viewModel::purchaseProduct,
        onBack = onBack,
        plate = uiState.plate,
        type = uiState.type,
        orderItems = uiState.orderItems,
        transactionFee = uiState.transactionFee,
        totalPrice = uiState.totalPrice,
    )
}

@Composable
fun PurchaseScreenContent(
    modifier: Modifier = Modifier,
    onOrderClick: () -> Unit = {},
    onBack: () -> Unit,
    plate: String,
    type: String,
    orderItems: List<PurchaseItemUiData> = emptyList(),
    transactionFee: Long = 0,
    totalPrice: Long = 0,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .padding(16.dp)
    ) {
        VignetteTitle(
            title = stringResource(R.string.order_screen_title),
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))

        VehicleInformation(
            plate = plate.uppercase(),
            type = type,
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(32.dp))

        OrderInfo(
            orderItems = orderItems,
            transactionFee = transactionFee,
        )

        Spacer(modifier = Modifier.height(32.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(32.dp))

        TotalPurchase(
            totalPrice = totalPrice.toPriceText(),
        )

        Spacer(modifier = Modifier.height(32.dp))

        VignetteButton(
            text = stringResource(R.string.next),
            onClick = { onOrderClick() },
        )

        Spacer(modifier = Modifier.height(16.dp))

        VignetteButton(
            buttonType = ButtonType.INVERSE,
            text = stringResource(R.string.cancel),
            onClick = { onBack() },
        )
    }
}

@Composable
fun VehicleInformation(
    modifier: Modifier = Modifier,
    plate: String,
    type: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.plate),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
            )

            Text(
                text = plate,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.vignette_type),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
            )

            Text(
                text = type,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun OrderInfo(
    modifier: Modifier = Modifier,
    orderItems: List<PurchaseItemUiData> = emptyList(),
    transactionFee: Long = 0,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        orderItems.forEachIndexed { index, orderItem ->
            OrderItem(
                name = orderItem.name,
                price = orderItem.price,
            )

            if (index != orderItems.lastIndex) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.usage_price),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
            )

            Text(
                text = transactionFee.toPriceText(),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

    }
}

@Composable
private fun OrderItem(
    modifier: Modifier = Modifier,
    name: String,
    price: Long,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = price.toPriceText(),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview
@Composable
private fun PurchaseScreenPreview() {
    VignetteTheme {
        Box(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.background,
            )
        ) {
            PurchaseScreenContent(
                onBack = {},
                plate = "ABC-123",
                type = "Éves vármegyei",
                orderItems = listOf(
                    PurchaseItemUiData(
                        name = "Baranya",
                        price = 5720,
                    ),
                    PurchaseItemUiData(
                        name = "Fejér",
                        price = 5720,
                    ),
                    PurchaseItemUiData(
                        name = "Győr-Moson Sopron",
                        price = 5720,
                    ),
                    PurchaseItemUiData(
                        name = "Pest",
                        price = 5720,
                    ),
                ),
                transactionFee = 110,
                totalPrice = 21910,
            )
        }
    }
}
