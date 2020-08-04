package com.gnova.bakingapp_kotlin.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gnova.bakingapp_kotlin.App
import com.gnova.bakingapp_kotlin.BakingApiStatus
import com.gnova.bakingapp_kotlin.R
import com.gnova.bakingapp_kotlin.ViewModelFactory
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<HomeViewModel>
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        viewModel.initViewModel()

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
                        observeUsers()
                    }

                }
            }
        })
    }

    private fun observeUsers() {
        viewModel.recipes.observe(this, Observer {
            it?.let {
                test_text.text = it[0].name
            }
        })
    }


}
