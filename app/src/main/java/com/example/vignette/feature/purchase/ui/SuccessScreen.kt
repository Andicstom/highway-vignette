package com.example.vignette.feature.purchase.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vignette.R
import com.example.vignette.core.ui.component.VignetteButton
import com.example.vignette.core.ui.theme.VignetteTheme

@Composable
fun SuccessScreen(
    onBack: () -> Unit,
) {
    SuccessScreenContent(
        onBack = onBack,
    )
}

@Composable
private fun SuccessScreenContent(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.confetti),
                contentDescription = stringResource(R.string.cd_confetti),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.BottomCenter)
                    .offset(y = 10.dp),
                text = stringResource(R.string.vignette_purchase_successfull),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )
        }

        Box(
            modifier = Modifier.weight(1f)
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.human),
                contentDescription = stringResource(R.string.cd_confetti),
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.BottomEnd,
            )
        }

        VignetteButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(R.string.ok),
            onClick = { onBack() }
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview
@Composable
private fun SuccessScreenPreview() {
    VignetteTheme {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.primary)
        ) {
            SuccessScreenContent()
        }
    }
}
