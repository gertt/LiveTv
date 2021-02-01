package com.gprifti.livetv.ui.splash

import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.gprifti.livetv.R
import com.gprifti.livetv.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var binding: FragmentSplashBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)
        navController = Navigation.findNavController(view)
        changeOpacity(binding.splashImg)
        changeView(view)
        view.findViewById<View>(R.id.splash_img).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_emailFragment)
        }
    }

    private fun changeOpacity(splashImg: ImageView) {
        val animation1 = AlphaAnimation(0.0f, 1.0f)
        animation1.duration = 2500
        animation1.fillAfter = true
        splashImg.startAnimation(animation1)
    }

    private fun changeView(view: View) {
        viewModel.changeView.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                0 -> Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_nav_graph_bottom)
                1 -> Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_emailFragment)
            }
        })
    }
}