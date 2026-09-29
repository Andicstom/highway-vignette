package com.example.vignette.feature.purchase.ui

import android.widget.Toast
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
import com.example.vignette.feature.purchase.ui.map.HungaryMap

@Composable
fun CountyScreen(
    viewModel: CountyViewModel = hiltViewModel(),
    openOrderScreen: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    val errorMessage = stringResource(R.string.error_message)
    val countiesAreNotNeighboursMessage = stringResource(R.string.counties_not_neighbours)

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvent.ShowErrorToast -> {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }

                is UiEvent.ShowCountiesNotNeighboursToast -> {
                    Toast.makeText(context, countiesAreNotNeighboursMessage, Toast.LENGTH_SHORT)
                        .show()
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

        HungaryMapContent(
            counties = counties,
            selectedCounties = selectedCounties,
            onCountySelected = onCountyClick,
        )

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
fun HungaryMapContent(
    modifier: Modifier = Modifier,
    counties: List<CountyUiData> = emptyList(),
    selectedCounties: List<CountyUiData> = emptyList(),
    onCountySelected: (county: CountyUiData) -> Unit = {},
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        HungaryMap(
            counties = counties.toCountyPathList(),
            selectedCounties = selectedCounties.toCountyPathList().map { it.id },
            onCountySelected = { countyId ->
                val county = counties.firstOrNull { it.id == countyId }
                if (county != null) {
                    onCountySelected(county)
                }
            },
        )
    }
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
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            CountyScreenContent(
                openOrderScreen = {},
                counties = listOf(
                    CountyUiData(
                        id = "YEAR_11",
                        name = "Bács-Kiskun",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "YEAR_12",
                        name = "Baranya",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "YEAR_13",
                        name = "Békés",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "YEAR_14",
                        name = "Borsod-Abaúj-Zemplén",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "YEAR_15",
                        name = "Csongrád-Csanád",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "YEAR_16",
                        name = "Fejér",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "YEAR_17",
                        name = "Győr-Moson-Sopron",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "YEAR_18",
                        name = "Hajdú-Bihar",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "YEAR_19",
                        name = "Heves",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "YEAR_20",
                        name = "Jász-Nagykun-Szolnok",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "YEAR_21",
                        name = "Komárom-Esztergom",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "YEAR_22",
                        name = "Nógrád",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "YEAR_23",
                        name = "Pest",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "YEAR_24",
                        name = "Somogy",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "YEAR_25",
                        name = "Szabolcs-Szatmár-Bereg",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "YEAR_26",
                        name = "Tolna",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "YEAR_27",
                        name = "Vas",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "YEAR_28",
                        name = "Veszprém",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "YEAR_29",
                        name = "Zala",
                        price = 5720,
                        transactionFee = 120,
                    ),
                ),
                selectedCounties = listOf(
                    CountyUiData(
                        id = "YEAR_11",
                        name = "Baranya",
                        price = 5720,
                        transactionFee = 120,
                    ),
                    CountyUiData(
                        id = "YEAR_29",
                        name = "Zala",
                        price = 5720,
                        transactionFee = 120,
                    ),
                ),
                onCountyClick = {},
            )
        }
    }
}
