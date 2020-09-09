package com.gnova.bakingapp_kotlin.ui.detail

import com.gnova.bakingapp_kotlin.api.models.DetailModel
import com.gnova.bakingapp_kotlin.api.models.Steps

sealed class DetailFragmentState

data class ShowDualPane(val steps: Steps) : DetailFragmentState()

data class ShowSinglePane(val steps: Steps) : DetailFragmentState()

data class ShowDetailModel(val detailModel: DetailModel) : DetailFragmentState()
