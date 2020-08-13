package com.gnova.bakingapp_kotlin.api.models

import android.os.Parcelable
import com.gnova.bakingapp_kotlin.api.models.Recipe
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailModel (
    val recipe: Recipe,
    val dualPane: Boolean
) : Parcelable