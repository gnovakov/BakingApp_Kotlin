package com.gnova.bakingapp_kotlin.api

import com.gnova.bakingapp_kotlin.api.models.Recipe
import io.reactivex.Single
import retrofit2.http.GET

interface BakingApi {

    @GET("baking.json")
    fun getRecipes(): Single<List<Recipe>>

}