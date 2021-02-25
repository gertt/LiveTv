package com.gprifti.livetv.ui.menu.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.gprifti.livetv.R
import com.gprifti.livetv.data.model.response.StreamsModel
import com.gprifti.livetv.databinding.FragmentSearchBinding
import com.gprifti.livetv.utils.SnackBar.Companion.snack
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var navController: NavController
    private  val viewModel: SearchViewModel by viewModels()
    @Inject lateinit var  ctx: Context


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        navController = Navigation.findNavController(view)

        setListener()
        initRecyclerView()
        changeFilterCategory()
        viewState()
        backPress()
    }

    private fun  setListener(){
        binding.edxSearch.doOnTextChanged { text, start, before, count ->
            text?.let {
                viewModel.onQueryTextChange(text)
            }
        }

        binding.btAll.setOnClickListener {
            viewModel.changeFilterState(1)
        }

        binding.btInternacional .setOnClickListener {
            viewModel.changeFilterState(2)
        }

        binding.btNational.setOnClickListener {
            viewModel.changeFilterState(3)
        }
    }

    @SuppressLint("WrongConstant")
    private fun initRecyclerView() {
        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(ctx, LinearLayout.VERTICAL, false)
        viewModel.searchResult.observe(viewLifecycleOwner, Observer {
            searchAdapter = SearchAdapter(ctx,navController,it as ArrayList<StreamsModel>,viewModel)
            binding.recyclerViewSearch.adapter = searchAdapter
        })
    }

    private fun changeFilterCategory() {
        viewModel.filterCategory.observe(viewLifecycleOwner, Observer { category ->
            when (category) {
                1 -> {
                    binding.btAll.background = ContextCompat.getDrawable(ctx, R.drawable.category_filter_background_selected)
                    binding.btInternacional.background = ContextCompat.getDrawable(ctx, R.drawable.category_filter_background_unselected)
                    binding.btNational.background = ContextCompat.getDrawable(ctx, R.drawable.category_filter_background_unselected)
                }
                2 -> {
                    binding.btInternacional.background = ContextCompat.getDrawable(ctx, R.drawable.category_filter_background_selected)
                    binding.btAll.background = ContextCompat.getDrawable(ctx, R.drawable.category_filter_background_unselected)
                    binding.btNational.background = ContextCompat.getDrawable(ctx,R.drawable.category_filter_background_unselected)
                }
                3 -> {
                    binding.btNational.background = ContextCompat.getDrawable(ctx, R.drawable.category_filter_background_selected)
                    binding.btAll.background = ContextCompat.getDrawable(ctx, R.drawable.category_filter_background_unselected)
                    binding.btInternacional.background = ContextCompat.getDrawable(ctx,R.drawable.category_filter_background_unselected)
                }
            }
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
                    navController.navigate(R.id.action_searchFragment_to_popularListFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}
