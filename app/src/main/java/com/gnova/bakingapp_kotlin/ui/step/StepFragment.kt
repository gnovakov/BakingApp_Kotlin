package com.gnova.bakingapp_kotlin.ui.step

import android.content.Context
import android.net.Uri
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
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.ExoPlayerFactory.newSimpleInstance
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.fragment_step.*
import javax.inject.Inject

class StepFragment : Fragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<StepFragmentViewModel>
    private lateinit var viewModel: StepFragmentViewModel

    private lateinit var exoPlayer: SimpleExoPlayer

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
        Log.d("TAG", "Step Fragment")

        viewModel = ViewModelProvider(this, viewModelFactory).get(StepFragmentViewModel::class.java)

        exoPlayer = newSimpleInstance(this.context, DefaultTrackSelector())

        // Get Argument that was passed from activity
        val step = arguments!!.getParcelable<Steps>("step")
        viewModel.onViewInit(step)

        observeSelectedStep()
    }

    private fun observeSelectedStep() {
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

        // Set Video URL
        var CONTENT_URL: String = if (step.videoURL.isEmpty())  {
            step.thumbnailURL
        } else {
            step.videoURL
        }

        //Initialise Video Player if there is a video URL available, otherwise remove the PlayerView Element
        if (CONTENT_URL.length == 0) {
            videoContainer.visibility = View.GONE
            exoPlayer.release()
            Log.d("TAG", "No Video Player")
        } else {
            initialisePlayer(CONTENT_URL)
            Log.d("TAG", "Yes Video Player")
        }

    }

    private fun initialisePlayer(contentUrl: String) {

        val appNameStringRes = R.string.app_name

        val userAgent = Util.getUserAgent( context, context!!.getString(appNameStringRes)
        )
        val defdataSourceFactory = DefaultDataSourceFactory(context, userAgent)
        val uriOfContentUrl = Uri.parse(contentUrl)
        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(defdataSourceFactory)
            .createMediaSource(uriOfContentUrl) // creating a media source


        exoPlayer.prepare(mediaSource)
        exoPlayer.setPlayWhenReady(true) // start loading video and play it at the moment a chunk of it is available offline


        pv_main.player = exoPlayer // attach surface to the view

    }

    // Stop & Release Player when we leave the page
    private fun stopPlayer() {
        exoPlayer.release()
    }

    private fun pausePlayer() {
        exoPlayer.playWhenReady = false
    }

    override fun onStop() {
        super.onStop()
        //stopPlayer();
        pausePlayer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopPlayer()
    }




}