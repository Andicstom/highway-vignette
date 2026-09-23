package com.example.vignette.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vignette.core.ui.theme.VignetteTheme

enum class ButtonType {
    DEFAULT,
    INVERSE,
}

@Composable
fun VignetteButton(
    modifier: Modifier = Modifier,
    buttonType: ButtonType = ButtonType.DEFAULT,
    text: String,
    onClick: () -> Unit,
) {

    val backgroundColor = when (buttonType) {
        ButtonType.DEFAULT -> MaterialTheme.colorScheme.onSurface
        ButtonType.INVERSE -> MaterialTheme.colorScheme.surface
    }

    val textColor = when (buttonType) {
        ButtonType.DEFAULT -> MaterialTheme.colorScheme.surface
        ButtonType.INVERSE -> MaterialTheme.colorScheme.onSurface
    }

    val borderColor = when (buttonType) {
        ButtonType.DEFAULT -> MaterialTheme.colorScheme.onSurface
        ButtonType.INVERSE -> MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(24.dp)
            )
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundColor)
            .clickable(onClick = { onClick() }),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview
@Composable
private fun VignetteButtonDefaultPreview() {
    VignetteTheme {
        VignetteButton(
            text = "Vásárlás",
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun VignetteButtonInversePreview() {
    VignetteTheme {
        VignetteButton(
            buttonType = ButtonType.INVERSE,
            text = "Vásárlás",
            onClick = {},
        )
    }
}
