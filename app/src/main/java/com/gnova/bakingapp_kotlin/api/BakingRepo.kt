package com.gnova.bakingapp_kotlin.api

import android.text.Editable
import com.gnova.bakingapp_kotlin.api.models.Recipe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BakingRepo @Inject constructor(private val bakingApi: BakingApi) {

    fun getRecipes(): Single<List<Recipe>> =
        bakingApi.getRecipes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}