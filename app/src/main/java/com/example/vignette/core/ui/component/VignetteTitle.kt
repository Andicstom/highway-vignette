package com.example.vignette.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.vignette.core.ui.theme.VignetteTheme

@Composable
fun VignetteTitle(
    modifier: Modifier = Modifier,
    title: String,
) {
    Text(
        modifier = modifier,
        text = title,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
    )
}

@Preview
@Composable
private fun VignetteTitlePreview() {
    VignetteTheme {
        Box(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
        ) {
            VignetteTitle(
                title = "Országos matricák",
            )
        }
    }
}
