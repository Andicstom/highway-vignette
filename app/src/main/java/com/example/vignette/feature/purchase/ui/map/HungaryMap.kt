package com.example.vignette.feature.purchase.ui.map

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import com.example.vignette.core.ui.theme.VignetteTheme

const val ORIGINAL_MAP_WIDTH = 313f
const val ORIGINAL_MAP_HEIGHT = 188f

@Composable
fun HungaryMap(
    modifier: Modifier = Modifier,
    counties: List<CountyPath> = emptyList(),
    selectedCounties: List<String> = emptyList(),
    onCountySelected: (String) -> Unit = {},
) {
    val selectedColor = MaterialTheme.colorScheme.primary
    val unselectedColor = Color.Gray

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(ORIGINAL_MAP_WIDTH / ORIGINAL_MAP_HEIGHT)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val scale = size.width / ORIGINAL_MAP_WIDTH
                    val transformedOffset = Offset(
                        x = offset.x / scale,
                        y = offset.y / scale
                    )
                    val selectedCounty = counties.firstOrNull { county ->
                        county.path.getBounds().contains(transformedOffset)
                    }?.id

                    if (selectedCounty != null) {
                        onCountySelected(selectedCounty)
                    }
                }
            }
    ) {
        val scale = size.width / ORIGINAL_MAP_WIDTH

        withTransform({
            scale(
                scaleX = scale,
                scaleY = scale,
                pivot = Offset.Zero,
            )
        }) {
            counties.forEach { county ->
                drawPath(
                    path = county.path,
                    color = if (selectedCounties.contains(county.id)) {
                        selectedColor
                    } else {
                        unselectedColor
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun HungaryMapPreview() {
    VignetteTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HungaryMap(
                    selectedCounties = listOf("YEAR_12", "YEAR_20"),
                    counties = listOf(
                        CountyPath(
                            id = "YEAR_11",
                            path = bácsKiskun,
                        ),
                        CountyPath(
                            id = "YEAR_12",
                            path = baranya,
                        ),
                        CountyPath(
                            id = "YEAR_13",
                            path = békés,
                        ),
                        CountyPath(
                            id = "YEAR_14",
                            path = borsodAbaújZemplén,
                        ),
                        CountyPath(
                            id = "YEAR_15",
                            path = csongrádCsanád,
                        ),
                        CountyPath(
                            id = "YEAR_16",
                            path = fejér,
                        ),
                        CountyPath(
                            id = "YEAR_17",
                            path = győrMosonSopron,
                        ),
                        CountyPath(
                            id = "YEAR_18",
                            path = hajdúBihar,
                        ),
                        CountyPath(
                            id = "YEAR_19",
                            path = heves,
                        ),
                        CountyPath(
                            id = "YEAR_20",
                            path = jászNagykunSzolnok,
                        ),
                        CountyPath(
                            id = "YEAR_21",
                            path = komáromEsztergom,
                        ),
                        CountyPath(
                            id = "YEAR_22",
                            path = nógrád,
                        ),
                        CountyPath(
                            id = "YEAR_23",
                            path = pest,
                        ),
                        CountyPath(
                            id = "YEAR_24",
                            path = somogy,
                        ),
                        CountyPath(
                            id = "YEAR_25",
                            path = szabolcsSzatmárBereg,
                        ),
                        CountyPath(
                            id = "YEAR_26",
                            path = tolna,
                        ),
                        CountyPath(
                            id = "YEAR_27",
                            path = vas,
                        ),
                        CountyPath(
                            id = "YEAR_28",
                            path = veszprém,
                        ),
                        CountyPath(
                            id = "YEAR_29",
                            path = zala,
                        )
                    ),
                )
            }
        }
    }
}
