package com.gnova.bakingapp_kotlin.api.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailModel (
    val recipe: Recipe = Recipe(),
    val dualPane: Boolean = false
) : Parcelable