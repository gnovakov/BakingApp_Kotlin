package com.gnova.bakingapp_kotlin.ui.step

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gnova.bakingapp_kotlin.App
import com.gnova.bakingapp_kotlin.R
import com.gnova.bakingapp_kotlin.ViewModelFactory
import com.gnova.bakingapp_kotlin.api.models.Steps
import kotlinx.android.synthetic.main.fragment_step.*
import javax.inject.Inject

class StepFragment : Fragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<StepFragmentViewModel>
    private lateinit var viewModel: StepFragmentViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(StepFragmentViewModel::class.java)

        // Get Argument that was passed from activity
        val step = arguments!!.getParcelable<Steps>("step")
        viewModel.onViewInit(step)

        observeStep()
    }

    private fun observeStep() {
        viewModel.selectedStep.observe(viewLifecycleOwner, Observer {
            it?.let {
                initialiseData(it)
            }
        })
    }

    private fun initialiseData(step: Steps) {

        // Set Title
        if (step.id.toString() == "0") {
            stepTitle.text = step.shortDescription
        } else {
            stepTitle.text = "Step ${step.id}: ${step.shortDescription}"
        }

        stepDescription.text = step.description



    }


}