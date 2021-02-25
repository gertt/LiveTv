package com.gprifti.livetv.ui.stream.details

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.gprifti.livetv.R
import com.gprifti.livetv.databinding.FragmentDetailsBinding
import com.gprifti.livetv.utils.IMAGE_URL
import com.gprifti.livetv.utils.ParseImage
import com.gprifti.livetv.utils.TITTLE_STREAM
import com.gprifti.livetv.utils.VIDEO_URL
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentDetailsBinding
    @Inject lateinit var ctx: Context

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentDetailsBinding.bind(view)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        eventListener()
        parseView()
        backPress()
    }

    private fun parseView() {
        with(ParseImage){
            parseImg(ctx, requireArguments().getString(IMAGE_URL), binding.imgHeader)
            parseImg(ctx, requireArguments().getString(IMAGE_URL), binding.imgPlay)
        }
        binding.txtTittle.text = requireArguments().getString(TITTLE_STREAM)
    }

    private fun eventListener() {
        binding.cardPlay.setOnClickListener {
            navController.navigate(
                R.id.action_detailsFragment2_to_playFragment,
                bundleOf(VIDEO_URL to requireArguments().getString(VIDEO_URL))
            )
        }
    }

    private fun backPress() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navController.navigate(R.id.action_detailsFragment2_to_favoriteFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}