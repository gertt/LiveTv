package com.gprifti.livetv.ui.menu.popular

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gprifti.livetv.R
import com.gprifti.livetv.data.model.response.StreamsModel
import com.gprifti.livetv.databinding.FragmentPopularBinding
import com.gprifti.livetv.utils.SnackBar.Companion.snack
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@AndroidEntryPoint
class PopularFragment : Fragment(R.layout.fragment_popular) {

    private lateinit var binding: FragmentPopularBinding
    private val viewModel: PopularViewModel by viewModels()
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var navController: NavController
    @Inject lateinit var  ctx: Context

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPopularBinding.bind(view)
        navController = Navigation.findNavController(view)

        setListener()
        initRecyclerView()
        viewState()
        backPress()
    }

    private fun setListener() {
        binding.edxSearch.doOnTextChanged { text, start, before, count ->
            text?.let {
                viewModel.onQueryTextChange(text)
            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun initRecyclerView() {
        binding.recyclerViewPopular.layoutManager = LinearLayoutManager(ctx, LinearLayout.VERTICAL, false)
        binding.recyclerViewPopular.layoutManager = GridLayoutManager(ctx, 3)
        viewModel.searchResult.observe(viewLifecycleOwner, Observer {
            popularAdapter = PopularAdapter(ctx,navController,it as ArrayList<StreamsModel>)
            binding.recyclerViewPopular.adapter = popularAdapter
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
                        activity?.finish()
                    }
                }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}
