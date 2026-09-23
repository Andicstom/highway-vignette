package com.example.vignette.feature.purchase.data.api.model

import com.google.gson.annotations.SerializedName

data class CountyResponseData(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
)
