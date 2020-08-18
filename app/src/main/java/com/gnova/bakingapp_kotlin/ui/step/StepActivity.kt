package com.gnova.bakingapp_kotlin.ui.step

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gnova.bakingapp_kotlin.App
import com.gnova.bakingapp_kotlin.Const.STEP
import com.gnova.bakingapp_kotlin.R
import com.gnova.bakingapp_kotlin.ViewModelFactory
import com.gnova.bakingapp_kotlin.api.models.Steps
import javax.inject.Inject


class StepActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<StepViewModel>
    private lateinit var viewModel: StepViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step)
        Log.d("TAG", "Step Activity")
        viewModel = ViewModelProvider(this, viewModelFactory).get(StepViewModel::class.java)

        intent.extras?.let {
            val step = it.get(STEP) as Steps
            viewModel.onViewInit(step)
        }

        inflateStepFrgment()

    }

    fun inflateStepFrgment() {

        viewModel.selectedStep.observe(this, Observer {
            it?.let {

                // Create a bundle to pass the data
                val data = Bundle() // Use bundle to pass data

                // Put data into bundle
                data.putParcelable("step", it)

                    val stepFragment: Fragment = StepFragment() // Get Fragment Instance

                    stepFragment.arguments = data // Set argument bundle to our fragment

                    // Begin the transaction
                    supportFragmentManager.beginTransaction() // Replace the contents of the container with the new fragment
                        .add(R.id.step_fragment2, stepFragment) // Complete the changes added above
                        .commit()
            }
        })
    }

}