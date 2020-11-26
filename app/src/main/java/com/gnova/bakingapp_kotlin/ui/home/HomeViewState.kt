package com.gnova.bakingapp_kotlin.ui.home

import com.gnova.bakingapp_kotlin.api.models.Recipe


sealed class HomeViewState {

    data class Presenting( val recipe: List<Recipe>) : HomeViewState()

    object Error : HomeViewState()

    object Loading : HomeViewState()
}