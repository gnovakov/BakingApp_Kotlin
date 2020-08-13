package com.gnova.bakingapp_kotlin.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gnova.bakingapp_kotlin.api.models.DetailModel
import javax.inject.Inject

class DetailFragmentViewModel @Inject constructor() : ViewModel() {

    // A Recipe
    private val _selectedRecipe = MutableLiveData<DetailModel>()
    val selectedRecipe: LiveData<DetailModel>
        get() = _selectedRecipe

    fun onViewInit(recipe: DetailModel?) {
        _selectedRecipe.value = recipe
    }

}