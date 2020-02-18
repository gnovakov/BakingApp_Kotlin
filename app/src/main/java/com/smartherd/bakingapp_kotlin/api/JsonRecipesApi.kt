package com.smartherd.bakingapp_kotlin.api

import com.smartherd.bakingapp_kotlin.models.Recipe
import retrofit2.Call
import retrofit2.http.GET

interface JsonRecipesApi {

    @GET("baking.json")
    fun getRecipes(): Call<List<Recipe>>

}