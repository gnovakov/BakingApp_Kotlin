package com.smartherd.bakingapp_kotlin.models

data class Recipe (

    val id : Int,
    val name : String,
    val ingredients : List<Ingredients>,
    val steps : List<Steps>,
    val servings : Int,
    val image : String
)

data class Ingredients (

    val quantity : Double,
    val measure : String,
    val ingredient : String
)

data class Steps (

    val id : Int,
    val shortDescription : String,
    val description : String,
    val videoURL : String,
    val thumbnailURL : String
)