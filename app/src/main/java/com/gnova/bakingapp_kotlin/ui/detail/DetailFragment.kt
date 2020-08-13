package com.gnova.bakingapp_kotlin.ui.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.gnova.bakingapp_kotlin.App
import com.gnova.bakingapp_kotlin.R
import com.gnova.bakingapp_kotlin.ViewModelFactory
import com.gnova.bakingapp_kotlin.api.models.DetailModel
import com.gnova.bakingapp_kotlin.api.models.Ingredients
import com.gnova.bakingapp_kotlin.api.models.Steps
import com.gnova.bakingapp_kotlin.ui.home.RecipeAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject

class DetailFragment : Fragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<DetailFragmentViewModel>
    private lateinit var viewModel: DetailFragmentViewModel

    private val ingredientsAdapter: IngredientsAdapter by lazy {
        IngredientsAdapter()
    }

    private val stepsAdapter: StepsAdapter by lazy {
        StepsAdapter()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailFragmentViewModel::class.java)

        // Get Argument that was passed from activity
        val data = arguments!!.getParcelable<DetailModel>("recipe")
        viewModel.onViewInit(data)

        setupRecyclerView()
        observeRecipe()
    }

    private fun observeRecipe() {
        viewModel.selectedRecipe.observe(viewLifecycleOwner, Observer {
            it?.let {
                initialiseData(it)
            }
        })
    }

    private fun initialiseData(data: DetailModel) {
        recipeName.text = data.recipe.name
        showIngredients(data.recipe.ingredients)
        showSteps(data.recipe.steps)
    }

    private fun showIngredients(ingredients: List<Ingredients>) {
        ingredientsAdapter.submitList(ingredients)
    }

    private fun showSteps(steps: List<Steps>) {
        stepsAdapter.submitList(steps)
    }

    private fun setupRecyclerView() {
        ingredients_recycler_view.setHasFixedSize(true)
        ingredients_recycler_view.layoutManager = GridLayoutManager(this.context, 1)
        ingredients_recycler_view.adapter = ingredientsAdapter
        steps_recycler_view.setHasFixedSize(true)
        steps_recycler_view.layoutManager = GridLayoutManager(this.context, 1)
        steps_recycler_view.adapter = stepsAdapter
    }

}