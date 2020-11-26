package com.gnova.bakingapp_kotlin.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.gnova.bakingapp_kotlin.App
import com.gnova.bakingapp_kotlin.BakingApiStatus
import com.gnova.bakingapp_kotlin.Const.RECIPE
import com.gnova.bakingapp_kotlin.R
import com.gnova.bakingapp_kotlin.ViewModelFactory
import com.gnova.bakingapp_kotlin.api.models.Recipe
import com.gnova.bakingapp_kotlin.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<HomeViewModel>
    private lateinit var viewModel: HomeViewModel

    private val adapter: RecipeAdapter by lazy {
        RecipeAdapter(RecipeAdapter.OnClickListener {
            launchDetailActivity(it)
        })
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
        viewModel.viewState.observe(this, Observer {
            when (it) {
                is HomeViewState.Presenting -> showRecipe(it.recipe)
                is Error -> Log.d("TAG", "ERROR")
            }
        })
    }

    private fun showRecipe(recipe: List<Recipe>) {
        adapter.submitList(recipe)

    }

    private fun launchDetailActivity(selectedRecipe: Recipe) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(RECIPE, selectedRecipe)
        startActivity(intent)
    }

    private fun setupRecyclerView() {
        recipe_recycler_view.let {
            it.setHasFixedSize(true)
            it.layoutManager = GridLayoutManager(this, 1)
            it.adapter = adapter
        }
    }


}
