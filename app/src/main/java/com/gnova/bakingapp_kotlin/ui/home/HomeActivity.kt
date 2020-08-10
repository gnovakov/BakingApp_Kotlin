package com.gnova.bakingapp_kotlin.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.gnova.bakingapp_kotlin.App
import com.gnova.bakingapp_kotlin.BakingApiStatus
import com.gnova.bakingapp_kotlin.R
import com.gnova.bakingapp_kotlin.ViewModelFactory
import com.gnova.bakingapp_kotlin.api.models.Recipe
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<HomeViewModel>
    private lateinit var viewModel: HomeViewModel

    private val adapter: RecipeAdapter by lazy {
        RecipeAdapter()
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        viewModel.initViewModel()

        setupRecyclerView()
        observeApiStatus()
    }

    private fun observeApiStatus() {
        viewModel.apiStatus.observe(this, Observer {
            it?.let {
                when (it) {
                    BakingApiStatus.LOADING -> {
                        Log.d("TAG", "LOADING")
                    }
                    BakingApiStatus.ERROR -> {
                        Log.d("TAG", "ERROR")
                    }
                    BakingApiStatus.DONE -> {
                        Log.d("TAG", "DONE")
                        observeRecipes()
                    }

                }
            }
        })
    }

    private fun observeRecipes() {
        viewModel.recipes.observe(this, Observer {
            it?.let {
                showRecipe(it)
            }
        })
    }

    private fun showRecipe(recipe: List<Recipe>) {
        adapter.submitList(recipe)

    }

    private fun setupRecyclerView() {
        recipe_recycler_view.setHasFixedSize(true)
        recipe_recycler_view.layoutManager = GridLayoutManager(this, 1)
        recipe_recycler_view.adapter = adapter
    }


}
