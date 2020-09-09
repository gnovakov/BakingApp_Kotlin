package com.gnova.bakingapp_kotlin.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gnova.bakingapp_kotlin.api.models.DetailModel
import com.gnova.bakingapp_kotlin.api.models.Steps
import javax.inject.Inject

class DetailFragmentViewModel @Inject constructor() : ViewModel() {

    // A Recipe
    private val _selectedRecipe = MutableLiveData<DetailFragmentState>()
    val selectedRecipe: LiveData<DetailFragmentState>
        get() = _selectedRecipe

    private var isDualPane: Boolean = false

    fun onViewInit(recipe: DetailModel) {
        _selectedRecipe.value = ShowDetailModel(recipe)
        isDualPane = recipe.dualPane
    }

    fun onItemClicked(steps: Steps) {
        if (isDualPane) {
            _selectedRecipe.value = ShowDualPane(steps)
        } else {
            _selectedRecipe.value = ShowSinglePane(steps)
        }
    }
}