package com.gnova.bakingapp_kotlin.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gnova.bakingapp_kotlin.App
import com.gnova.bakingapp_kotlin.Const.RECIPE
import com.gnova.bakingapp_kotlin.R
import com.gnova.bakingapp_kotlin.ViewModelFactory
import com.gnova.bakingapp_kotlin.api.models.Recipe
import javax.inject.Inject

// Detail Activity - Main Screen with list of Ingredients and Steps for a chosen recipe
    // - DetailActivity - is a holder that holds the Steps Fragment(which contains the ist of Ingredients and Steps for a chosen recipe)
        // When a step is clicked the Step Detail Activity is loaded which holds the step detail Fragment which contains Details for an individual step (video, description etc...)

// Step Detail Activity - Details for an individual step (video, description etc...)

class DetailActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<DetailViewModel>
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)

        intent.extras?.let {
            val recipe = it.get(RECIPE) as Recipe
            viewModel.onViewInit(recipe)
        }

        //Inflate DetailFragment whilst passing data
        if (savedInstanceState == null) {
            inflateDetailFrgment()
        }

    }


    fun inflateDetailFrgment() {

        viewModel.selectedRecipe.observe(this, Observer {
            it?.let {

        // Create a bundle to pass the data
        val data = Bundle() // Use bundle to pass data

        // Put data into bundle
        data.putParcelable("recipe", it)

        if(it.recipe.name != null) {
            val detailFragment: Fragment = DetailFragment() // Get Fragment Instance

            detailFragment.arguments = data // Set argument bundle to our fragment

            // Begin the transaction
            supportFragmentManager.beginTransaction() // Replace the contents of the container with the new fragment
                .add(R.id.detail_fragment, detailFragment) // Complete the changes added above
                .commit()
        } else {
            Toast.makeText(this, "Unable To Launch Fragment Transaction", Toast.LENGTH_SHORT).show()
            finish()
        }

            }
        })
    }

}