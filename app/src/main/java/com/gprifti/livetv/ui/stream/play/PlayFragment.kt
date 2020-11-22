package com.gprifti.livetv.ui.stream.play

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import com.gprifti.livetv.R
import com.gprifti.livetv.databinding.FragmentPlayBinding
import com.gprifti.livetv.utils.InternetConnection
import com.gprifti.livetv.utils.SnackBar.Companion.snack
import kotlinx.android.synthetic.main.fragment_play.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



       var HLS_STATIC_URL = ""
const val STATE_RESUME_WINDOW = "resumeWindow"
const val STATE_RESUME_POSITION = "resumePosition"
const val STATE_PLAYER_FULLSCREEN = "playerFullscreen"
const val STATE_PLAYER_PLAYING = "playerOnPlay"


@RequiresApi(Build.VERSION_CODES.M)
class PlayFragment : Fragment(), Player.EventListener {

    private lateinit var binding: FragmentPlayBinding
    private lateinit var viewModel: PlayViewModel

    var scope = MainScope()

    private var firstTimeBackPress: Long = 0

    private lateinit var navController: NavController

    private lateinit var exoPlayer: SimpleExoPlayer
    private lateinit var dataSourceFactory: DataSource.Factory
    private lateinit var ctx: Context
    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private var isFullscreen = false
    private var isPlayerPlaying = true

    private val mediaItem = MediaItem.Builder()
        .setUri(HLS_STATIC_URL)
        .setMimeType(MimeTypes.APPLICATION_M3U8)
        .build()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ctx = container!!.context

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_play, container, false)
        viewModel = ViewModelProvider(this).get(PlayViewModel::class.java)
        binding.playViewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        var recipient = requireArguments().getString("videoUrl")

        if (recipient != null) {
            HLS_STATIC_URL =  recipient
        }

        dataSourceFactory = DefaultDataSourceFactory(ctx, Util.getUserAgent(ctx, "testapp"))
        if (savedInstanceState != null) {
            currentWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW)
            playbackPosition = savedInstanceState.getLong(STATE_RESUME_POSITION)
            isFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN)
            isPlayerPlaying = savedInstanceState.getBoolean(STATE_PLAYER_PLAYING)
        }
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        backPress()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(STATE_RESUME_WINDOW, exoPlayer.currentWindowIndex)
        outState.putLong(STATE_RESUME_POSITION, exoPlayer.currentPosition)
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, isFullscreen)
        outState.putBoolean(STATE_PLAYER_PLAYING, isPlayerPlaying)
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        binding.includedLayoutLoader.progressBar.visibility = View.VISIBLE
        if (Util.SDK_INT > 23) {
            initPlayer()
            exoPlayer.addListener(this)
            if (player_view != null) player_view.onResume()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23) {
            initPlayer()
            if (player_view != null) player_view.onResume()
        }
    }

    override fun onStop() {
        releaseExoplayer()
        super.onStop()
    }

    private fun releaseExoplayer() {
        playbackPosition = exoPlayer.currentPosition
        exoPlayer.release()
    }

    private fun initPlayer() {
        exoPlayer = SimpleExoPlayer.Builder(this!!.requireContext()).build().apply {
            playWhenReady = isPlayerPlaying
            seekTo(currentWindow, playbackPosition)
            setMediaItem(mediaItem, false)
            prepare()
        }
        player_view.player = exoPlayer
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == Player.STATE_BUFFERING)
            binding.includedLayoutLoader.progressBar.visibility = View.VISIBLE
        else if (playbackState == Player.STATE_READY)
            binding.includedLayoutLoader.progressBar.visibility = View.INVISIBLE
        else if (playbackState == Player.STATE_IDLE) {
            binding.includedLayoutLoader.progressBar.visibility = View.VISIBLE
            startCheck()
        }
    }

    fun startCheck() {
        scope.launch {
            while (true) {
                if (InternetConnection.isOnline(ctx)) {
                    onStart()
                    binding.includedLayoutLoader.progressBar.visibility = View.INVISIBLE
                    stopCheck()
                }
                delay(1500)
            }
        }
    }

    fun stopCheck() {
        scope.cancel()
        scope = MainScope()
    }

    private fun backPress() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if ((firstTimeBackPress - System.currentTimeMillis()) > 1200)
                        view?.snack("Press again")
                    else view?.snack("ok again")
                    navController.navigate(R.id.action_searchFragment_to_popularListFragment)

                    firstTimeBackPress = System.currentTimeMillis()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}