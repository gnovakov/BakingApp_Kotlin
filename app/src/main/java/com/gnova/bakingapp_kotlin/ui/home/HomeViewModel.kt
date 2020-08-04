package com.gnova.bakingapp_kotlin.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.gnova.bakingapp_kotlin.BakingApiStatus
import com.gnova.bakingapp_kotlin.api.BakingRepo
import com.gnova.bakingapp_kotlin.api.models.Recipe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val bakingRepo: BakingRepo): ViewModel()  {

    // The most recent API response
    private val _apiStatus = MutableLiveData<BakingApiStatus>()
    val apiStatus: LiveData<BakingApiStatus>
        get() = _apiStatus

    // A Recipe
    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>>
        get() = _recipes


    fun initViewModel() {
        getRecipes()
    }

    private fun getRecipes() {
        // Using Rx
        add(bakingRepo.getRecipes().subscribe(
            {
                _apiStatus.value = BakingApiStatus.LOADING
                Log.d("TAG", "VM LOADING")
                _recipes.value = it
                _apiStatus.value = BakingApiStatus.DONE
                Log.d("TAG", "VM DONE")
            }, {
                _apiStatus.value = BakingApiStatus.ERROR
                Log.d("TAG", "VM ERROR")
                _recipes.value = ArrayList()
            }

        ))
    }

    val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    protected fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cleanUp() {
        compositeDisposable.clear()
    }

}