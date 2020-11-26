package com.gnova.bakingapp_kotlin.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.gnova.bakingapp_kotlin.BakingApiStatus
import com.gnova.bakingapp_kotlin.api.BakingRepo
import com.gnova.bakingapp_kotlin.api.models.Recipe
import com.gnova.bakingapp_kotlin.ui.home.HomeViewState.Error
import com.gnova.bakingapp_kotlin.ui.home.HomeViewState.Loading
import com.gnova.bakingapp_kotlin.ui.home.HomeViewState.Presenting
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val bakingRepo: BakingRepo): ViewModel()  {

    // View State Version
    private val _viewState = MutableLiveData<HomeViewState>()
    val viewState: LiveData<HomeViewState>
        get() = _viewState

    fun initViewModel() {
        getRecipes()
    }

    private fun getRecipes() {
        // Using Rx
        _viewState.value = Loading
        Log.d("TAG", "VM LOADING")
        add(
            bakingRepo.getRecipes()
                .subscribe(
            {
                _viewState.value = Presenting(it)
                Log.d("TAG", "VM DONE")
            }, {
                    _viewState.value = Error
                    Log.d("TAG", "VM ERROR")
            }

        ))
    }

    val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    protected fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}