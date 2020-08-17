package com.gnova.bakingapp_kotlin.ui.step

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gnova.bakingapp_kotlin.App
import com.gnova.bakingapp_kotlin.R
import com.gnova.bakingapp_kotlin.ViewModelFactory
import com.gnova.bakingapp_kotlin.api.models.Steps
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.android.synthetic.main.fragment_step.*
import javax.inject.Inject

class StepFragment : Fragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<StepFragmentViewModel>
    private lateinit var viewModel: StepFragmentViewModel

    private var playbackPosition = 0
    private lateinit var mediaController: MediaController

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

        mediaController = MediaController(this.context)

        videoView.setOnPreparedListener {
            mediaController.setAnchorView(videoContainer)
            videoView.setMediaController(mediaController)
            videoView.seekTo(playbackPosition)
            videoView.start()
        }

        videoView.setOnInfoListener { player, what, extra ->
            if(what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                progressBar.visibility = View.INVISIBLE
            true
        }

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

        // Set Video URL
        var CONTENT_URL: String = if (step.videoURL.isEmpty())  {
            step.thumbnailURL
        } else {
            step.videoURL
        }

        //Initialise Video Player if there is a video URL available, otherwise remove the PlayerView Element


        //Initialise Video Player if there is a video URL available, otherwise remove the PlayerView Element
        if (CONTENT_URL.length == 0) {
            videoContainer.visibility = PlayerView.GONE
        } else {
            initialisePlayer(CONTENT_URL)
        }

    }

    private fun initialisePlayer(contentUrl: String) {
        val uri = Uri.parse(contentUrl)
        videoView.setVideoURI(uri)
        progressBar.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
        videoView.pause()
        playbackPosition =  videoView.currentPosition
    }

    override fun onStop() {
        videoView.stopPlayback()
        super.onStop()
    }




}