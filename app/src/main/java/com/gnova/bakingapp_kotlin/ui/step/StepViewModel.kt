package com.gnova.bakingapp_kotlin.ui.step

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gnova.bakingapp_kotlin.api.models.DetailModel
import com.gnova.bakingapp_kotlin.api.models.Recipe
import com.gnova.bakingapp_kotlin.api.models.Steps
import javax.inject.Inject

class StepViewModel @Inject constructor() : ViewModel() {

    // A Step
    private val _selectedStep = MutableLiveData<Steps>()
    val selectedStep: LiveData<Steps>
        get() = _selectedStep

    fun onViewInit(step: Steps) {
        _selectedStep.value = step
    }



}