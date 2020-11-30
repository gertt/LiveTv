package com.gprifti.livetv.ui.menu.favorite

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.gprifti.livetv.R
import com.gprifti.livetv.data.db.LiveTvDatabase
import com.gprifti.livetv.data.pref.PrefStorage
import com.gprifti.livetv.data.repository.Repository
import com.gprifti.livetv.databinding.FragmentFavoriteBinding
import com.gprifti.livetv.utils.SnackBar.Companion.snack


class FavoriteFragment : Fragment() {


    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var ctx: Context
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        ctx = container!!.context
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        val repository = Repository(LiveTvDatabase(ctx), PrefStorage(ctx))
        val viewModelProviderFactory = FavoriteProviderFactory(ctx, repository)

        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(FavoriteViewModel::class.java)
        binding.favoriteViewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        initRecyclerView()
        viewState()
        backPress()
    }

    @SuppressLint("WrongConstant")
    private fun initRecyclerView() {
        binding.recyclerViewFavorite.layoutManager = LinearLayoutManager(ctx, LinearLayout.VERTICAL, false)
        viewModel.favoriteResult.observe(viewLifecycleOwner, Observer {
            favoriteAdapter = FavoriteAdapter(it)
            binding.recyclerViewFavorite.adapter = favoriteAdapter
        })
    }
    private fun viewState() {
        viewModel.stateView.observe(viewLifecycleOwner, Observer { viewState ->

            when (viewState) {
                1 -> {
                    binding.includedLayoutView.loadView.visibility = View.VISIBLE
                    binding.includedLayoutLoader.progressBar.visibility = View.VISIBLE
                }
                2 -> {
                    binding.includedLayoutView.loadView.visibility = View.VISIBLE
                    binding.includedLayoutLoader.progressBar.visibility = View.INVISIBLE
                    view?.snack(getString(R.string.snack_no_internet))
                }
                3 -> {
                    binding.includedLayoutView.loadView.visibility = View.VISIBLE
                    binding.includedLayoutLoader.progressBar.visibility = View.INVISIBLE
                    view?.snack(getString(R.string.snack_something_wrong))
                }
                4 -> {
                    binding.includedLayoutView.loadView.visibility = View.INVISIBLE
                    binding.includedLayoutLoader.progressBar.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun backPress() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navController.navigate(R.id.action_favoriteFragment_to_searchFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}
