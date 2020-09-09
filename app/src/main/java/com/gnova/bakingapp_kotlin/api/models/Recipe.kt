package com.gnova.bakingapp_kotlin.api.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe (
    val id : Int = 0,
    val name : String = "",
    val ingredients : List<Ingredients> = emptyList(),
    val steps : List<Steps> = emptyList(),
    val servings : Int = 0,
    val image : String = ""
) : Parcelable

@Parcelize
data class Ingredients (
    val quantity : Double = 0.0,
    val measure : String = "",
    val ingredient : String = ""
) : Parcelable

@Parcelize
data class Steps (
    val id : Int = 0,
    val shortDescription : String = "",
    val description : String = "",
    val videoURL : String = "",
    val thumbnailURL : String = ""
) : Parcelable