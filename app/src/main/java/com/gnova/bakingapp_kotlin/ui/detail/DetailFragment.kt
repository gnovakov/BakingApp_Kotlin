package com.gnova.bakingapp_kotlin.ui.detail

import android.content.Context
import android.content.Intent
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
import com.gnova.bakingapp_kotlin.Const
import com.gnova.bakingapp_kotlin.R
import com.gnova.bakingapp_kotlin.ViewModelFactory
import com.gnova.bakingapp_kotlin.api.models.DetailModel
import com.gnova.bakingapp_kotlin.api.models.Ingredients
import com.gnova.bakingapp_kotlin.api.models.Steps
import com.gnova.bakingapp_kotlin.ui.step.StepActivity
import com.gnova.bakingapp_kotlin.ui.step.StepFragment
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
        StepsAdapter(StepsAdapter.OnClickListener {
            launchSteplActivity(it)
        })
    }

    private var dualPane = false

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
        dualPane = data.dualPane
    }

    private fun showIngredients(ingredients: List<Ingredients>) {
        ingredientsAdapter.submitList(ingredients)
    }

    private fun showSteps(steps: List<Steps>) {
        stepsAdapter.submitList(steps)
    }

    private fun launchSteplActivity(selectedStep: Steps) {

        if(dualPane) {
            Log.d("TAG", "dualPane")
            // Create a bundle to pass the data
            val step = Bundle() // Use bundle to pass data

            // Put data into bundle
            step.putParcelable("step", selectedStep)

            val stepFragment: Fragment = StepFragment() // Get Fragment Instance

            stepFragment.arguments = step // Set argument bundle to our fragment

            // Begin the transaction
            parentFragmentManager.beginTransaction() // Replace the contents of the container with the new fragment
                .replace(R.id.step_fragment, stepFragment) // Complete the changes added above
                .commit()


        } else {
            Log.d("TAG", "non dualPane")
            val intent = Intent(this.context, StepActivity::class.java)
            intent.putExtra(Const.STEP, selectedStep)
            startActivity(intent)
        }

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