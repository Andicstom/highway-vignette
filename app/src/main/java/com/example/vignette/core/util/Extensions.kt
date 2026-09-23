package com.example.vignette.core.util

import java.text.NumberFormat

fun Long.toPriceText(): String {
    val formatter = NumberFormat.getNumberInstance()
    return "${formatter.format(this)} Ft"
}
