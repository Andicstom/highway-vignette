package com.example.vignette.feature.purchase.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.vignette.R
import com.example.vignette.core.ui.UiEvent
import com.example.vignette.core.ui.component.VignetteButton
import com.example.vignette.core.ui.component.VignetteTitle
import com.example.vignette.core.ui.theme.VignetteTheme
import com.example.vignette.core.util.toPriceText
import com.example.vignette.feature.purchase.data.repository.model.VignetteTypes

@Composable
fun VignetteScreen(
    viewModel: VignetteViewModel = hiltViewModel(),
    openCountyScreen: () -> Unit,
    openOrderScreen: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    val errorMessage = stringResource(R.string.error_message)

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvent.ShowErrorToast -> {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    VignetteScreenContent(
        openCountyScreen = openCountyScreen,
        openOrderScreen = openOrderScreen,
        vehicleProfileUiData = uiState.vehicleProfileUiData,
        vignetteOptionUiData = uiState.vignetteOptionUiData,
        selectedVignetteOptionUiData = uiState.selectedVignetteOptionUiData,
        onVignetteOptionSelected = viewModel::onVignetteOptionSelected,
        resetSelectedVignetteOption = viewModel::resetSelectedVignetteOption,
    )
}

@Composable
private fun VignetteScreenContent(
    modifier: Modifier = Modifier,
    openCountyScreen: () -> Unit,
    openOrderScreen: () -> Unit,
    resetSelectedVignetteOption: () -> Unit,
    vehicleProfileUiData: VehicleProfileUiData?,
    vignetteOptionUiData: List<VignetteOptionUiData>,
    selectedVignetteOptionUiData: VignetteOptionUiData?,
    onVignetteOptionSelected: (vignetteOptionUiData: VignetteOptionUiData) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceDim)
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .padding(16.dp)
    ) {
        VehicleProfileCard(
            plate = vehicleProfileUiData?.registration?.uppercase() ?: "",
            name = vehicleProfileUiData?.ownerName ?: "",
        )

        Spacer(modifier = Modifier.height(16.dp))

        VignetteOptionsSelector(
            vignetteOptionUiData = vignetteOptionUiData,
            selectedVignetteOptionUiData = selectedVignetteOptionUiData,
            onVignetteOptionSelected = onVignetteOptionSelected,
            openOrderScreen = openOrderScreen,
        )

        Spacer(modifier = Modifier.height(16.dp))

        CountySelectorCard(
            onClick = {
                resetSelectedVignetteOption()
                openCountyScreen()
            }
        )
    }
}

@Composable
private fun VehicleProfileCard(
    plate: String,
    name: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(40.dp),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_car),
                    contentDescription = stringResource(R.string.cd_vihicle_type),
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = plate,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Composable
private fun VignetteOptionsSelector(
    vignetteOptionUiData: List<VignetteOptionUiData>,
    selectedVignetteOptionUiData: VignetteOptionUiData?,
    onVignetteOptionSelected: (vignetteOptionUiData: VignetteOptionUiData) -> Unit,
    openOrderScreen: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Column {
            VignetteTitle(
                title = stringResource(R.string.vignette_title),
            )

            Spacer(modifier = Modifier.height(14.dp))

            vignetteOptionUiData.forEachIndexed { index, option ->
                VignetteCard(
                    name = option.title,
                    price = option.price,
                    selected = option == selectedVignetteOptionUiData,
                    onClick = {
                        onVignetteOptionSelected(option)
                    }
                )

                if (index != vignetteOptionUiData.lastIndex) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            VignetteButton(
                text = stringResource(R.string.purchase),
                onClick = { openOrderScreen() }
            )
        }
    }
}

@Composable
private fun VignetteCard(
    name: String,
    price: Long,
    selected: Boolean,
    onClick: (id: String) -> Unit
) {
    val borderColor = if (selected) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.surfaceDim
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = { onClick(name) })
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        VignetteRadioButton(selected = selected)

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = name,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = price.toPriceText(),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun VignetteRadioButton(
    selected: Boolean,
) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .border(
                width = 2.dp,
                color = if (selected) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.surfaceDim
                },
                shape = CircleShape
            )
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onSurface)
            )
        }
    }
}

@Composable
private fun CountySelectorCard(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick() })
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.county_selector),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
            )

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_right_arrow),
                contentDescription = stringResource(R.string.cd_next_arrow),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Preview
@Composable
private fun VignetteScreenPreview() {
    VignetteTheme {
        VignetteScreenContent(
            openCountyScreen = {},
            openOrderScreen = {},
            vehicleProfileUiData = VehicleProfileUiData(
                registration = "ABC-123",
                ownerName = "John Doe",
                category = "Car",
            ),
            vignetteOptionUiData = listOf(
                VignetteOptionUiData(
                    type = VignetteTypes.DAY,
                    title = "D1 - napi (1 napos)",
                    vehicleCategory = "Car",
                    price = 5150,
                    transactionFee = 100,
                    category = "D1"
                ),
                VignetteOptionUiData(
                    type = VignetteTypes.DAY,
                    title = "D1 - heti (10 napos)",
                    vehicleCategory = "Car",
                    price = 6400,
                    transactionFee = 100,
                    category = "D1"
                ),
                VignetteOptionUiData(
                    type = VignetteTypes.DAY,
                    title = "D1 - havi",
                    vehicleCategory = "Car",
                    price = 10360,
                    transactionFee = 100,
                    category = "D1"
                )
            ),
            selectedVignetteOptionUiData = VignetteOptionUiData(
                type = VignetteTypes.DAY,
                title = "D1 - heti (10 napos)",
                vehicleCategory = "Car",
                price = 6400,
                transactionFee = 100,
                category = "D1"
            ),
            onVignetteOptionSelected = {},
            resetSelectedVignetteOption = {},
        )
    }
}


@Preview
@Composable
private fun VehicleProfileCardPreview() {
    VignetteTheme {
        VehicleProfileCard(
            plate = "ABC-123",
            name = "John Doe",
        )
    }
}
