package com.gprifti.livetv.ui.splash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.gprifti.livetv.R
import com.gprifti.livetv.data.db.LiveTvDatabase
import com.gprifti.livetv.data.pref.PrefStorage
import com.gprifti.livetv.data.repository.Repository
import com.gprifti.livetv.databinding.FragmentSplashBinding



class SplashFragment : Fragment() {

    private lateinit var viewModel: SplashViewModel
    private lateinit var navController: NavController
    private lateinit var binding: FragmentSplashBinding
    private lateinit var ctx: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ctx = container!!.context
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)

        val repository = Repository(LiveTvDatabase(ctx), PrefStorage(ctx))
        val viewModelProviderFactory = SplashProviderFactory(ctx,repository)
        viewModel = ViewModelProvider(this , viewModelProviderFactory).get(SplashViewModel::class.java)
        binding.splashViewModel = viewModel
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
         changeOpacity(binding.splashImg)
         changeView(view)
        view.findViewById<View>(R.id.splash_img).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_emailFragment)
        }
    }

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//
//        val view = inflater.inflate(R.layout.fragment_splash, container, false)
//
//        view.findViewById<View>(R.id.splash_img).setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_emailFragment)
//        }
//        return view
//
//        ctx = container!!.context
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
//        val repository = Repository(LiveTvDatabase(ctx), PrefStorage(ctx))
//        val viewModelProviderFactory = SplashProviderFactory(ctx, repository)
//
//        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(SplashViewModel::class.java)
//        binding.splashViewModel = viewModel
//        binding.lifecycleOwner = this
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//       // navController = Navigation.findNavController(view)
//       // changeOpacity(binding.splashImg)
//       // changeView(view)
//        Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_emailFragment)
//    }

    private fun changeOpacity(splashImg: ImageView) {
        val animation1 = AlphaAnimation(0.0f, 1.0f)
        animation1.duration = 2500
        animation1.fillAfter = true
        splashImg.startAnimation(animation1)
    }

    private fun changeView(view: View) {
        viewModel.changeView.observe(viewLifecycleOwner, Observer {state ->
          when(state){
              0 ->    Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_emailFragment)
              1 ->   Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_emailFragment)
          }
        })
    }
}