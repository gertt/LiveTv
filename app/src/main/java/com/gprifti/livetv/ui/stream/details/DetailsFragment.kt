package com.gprifti.livetv.ui.stream.details

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.gprifti.livetv.R
import com.gprifti.livetv.data.db.LiveTvDatabase
import com.gprifti.livetv.data.pref.PrefStorage
import com.gprifti.livetv.data.repository.Repository
import com.gprifti.livetv.databinding.FragmentDetailsBinding
import com.gprifti.livetv.utils.Constants.Companion.IMAGE_URL
import com.gprifti.livetv.utils.Constants.Companion.VIDEO_URL
import com.gprifti.livetv.utils.ParseImage

class DetailsFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var ctx: Context
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        ctx = container!!.context
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        val repository = Repository(LiveTvDatabase(ctx), PrefStorage(ctx))
        val viewModelProviderFactory = DetailsProviderFactory(ctx, repository)

        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(DetailsViewModel::class.java)
        binding.detailsViewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        ParseImage.parseImg(ctx, requireArguments().getString(IMAGE_URL), binding.imgHeader)
        ParseImage.parseImg(ctx, requireArguments().getString(IMAGE_URL), binding.imgPlay)

        binding.cardPlay.setOnClickListener({
            navController.navigate(
                    R.id.action_detailsFragment2_to_playFragment, bundleOf(VIDEO_URL to requireArguments().getString(VIDEO_URL))
            )
        })
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        backPress()
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