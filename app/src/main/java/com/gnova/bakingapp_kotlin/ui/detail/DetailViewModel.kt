package com.gnova.bakingapp_kotlin.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gnova.bakingapp_kotlin.R.id.step_fragment
import com.gnova.bakingapp_kotlin.api.models.DetailModel
import com.gnova.bakingapp_kotlin.api.models.Recipe
import javax.inject.Inject

class DetailViewModel @Inject constructor() : ViewModel() {

    // A Recipe
    private val _selectedRecipe = MutableLiveData<DetailModel>()
    val selectedRecipe: LiveData<DetailModel>
        get() = _selectedRecipe

    fun onViewInit(recipe: Recipe, dualPane: Boolean) {
        editData(recipe, dualPane)
    }

    private fun editData(recipe: Recipe, dualPane: Boolean) {

        val detailModel = DetailModel(recipe, dualPane)

        _selectedRecipe.value = detailModel


    }


}