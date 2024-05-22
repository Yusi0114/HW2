package com.example.penghuattraction.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Attraction(
    val id: Int,
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int,
    @StringRes val descriptionResourceId: Int,
    val latitude:Double,
    val  longitude:Double
)


