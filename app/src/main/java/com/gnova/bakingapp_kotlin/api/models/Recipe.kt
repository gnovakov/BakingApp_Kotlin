package com.gnova.bakingapp_kotlin.api.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe (
    val id : Int?,
    val name : String?,
    val ingredients : List<Ingredients>,
    val steps : List<Steps>,
    val servings : Int?,
    val image : String?
) : Parcelable

@Parcelize
data class Ingredients (
    val quantity : Double?,
    val measure : String?,
    val ingredient : String?
) : Parcelable

@Parcelize
data class Steps (
    val id : Int?,
    val shortDescription : String?,
    val description : String?,
    val videoURL : String?,
    val thumbnailURL : String?
) : Parcelable