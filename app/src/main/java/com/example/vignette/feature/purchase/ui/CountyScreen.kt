package com.example.vignette.feature.purchase.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.vignette.R
import com.example.vignette.core.ui.UiEvent
import com.example.vignette.core.ui.component.TotalPurchase
import com.example.vignette.core.ui.component.VignetteButton
import com.example.vignette.core.ui.component.VignetteTitle
import com.example.vignette.core.ui.theme.VignetteTheme
import com.example.vignette.core.util.toPriceText

@Composable
fun CountyScreen(
    viewModel: CountyViewModel = hiltViewModel(),
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

    CountyScreenContent(
        openOrderScreen = openOrderScreen,
        counties = uiState.counties,
        selectedCounties = uiState.selectedCounties,
        onCountyClick = viewModel::onCountyClick,
        totalPrice = uiState.totalPriceText,
    )
}

@Composable
fun CountyScreenContent(
    modifier: Modifier = Modifier,
    openOrderScreen: () -> Unit,
    counties: List<CountyUiData> = emptyList(),
    selectedCounties: List<CountyUiData> = emptyList(),
    onCountyClick: (county: CountyUiData) -> Unit,
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
            title = stringResource(R.string.county_title),
        )

        Spacer(modifier = Modifier.height(22.dp))

        HungaryMap()

        Spacer(modifier = Modifier.height(22.dp))

        CountySelector(
            counties = counties,
            selectedCounties = selectedCounties,
            onCountyClick = onCountyClick,
        )

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(24.dp))

        TotalPurchase(
            totalPrice = totalPrice.toPriceText(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        VignetteButton(
            text = stringResource(R.string.next),
            onClick = openOrderScreen,
        )
    }
}

@Composable
fun HungaryMap() {
    Image(
        modifier = Modifier.fillMaxWidth(),
        painter = painterResource(id = R.drawable.hungary_map),
        contentDescription = stringResource(R.string.cd_hungary_map),
    )
}

@Composable
fun CountySelector(
    counties: List<CountyUiData>,
    selectedCounties: List<CountyUiData>,
    onCountyClick: (county: CountyUiData) -> Unit,
) {

    counties.forEachIndexed { index, county ->
        CountyItem(
            county = county,
            selected = selectedCounties.contains(county),
            onClick = onCountyClick,
        )

        if (index != counties.lastIndex) {
            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}

@Composable
fun CountyItem(
    modifier: Modifier = Modifier,
    county: CountyUiData,
    selected: Boolean,
    onClick: (county: CountyUiData) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = selected,
            onCheckedChange = {
                onClick(county)
            }
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = county.name,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
        )

        Text(
            text = county.price.toPriceText(),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview
@Composable
private fun CountyScreenPreview() {
    VignetteTheme {
        Box(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
        ) {
            CountyScreenContent(
                openOrderScreen = {},
                counties = listOf(
                    CountyUiData(
                        id = "1",
                        name = "Baranya",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "2",
                        name = "Somogy",
                        price = 0,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "3",
                        name = "Zala",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "4",
                        name = "Veszprém",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "5",
                        name = "Fejár",
                        price = 5720,
                        transactionFee = 120,
                    ),
                ),
                selectedCounties = listOf(
                    CountyUiData(
                        id = "1",
                        name = "Baranya",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "4",
                        name = "Veszprém",
                        price = 5720,
                        transactionFee = 120,
                    ),
                ),
                onCountyClick = {},
            )
        }
    }
}
