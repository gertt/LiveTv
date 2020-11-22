package com.gprifti.livetv.ui.menu.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.gprifti.livetv.R
import com.gprifti.livetv.data.db.LiveTvDatabase
import com.gprifti.livetv.data.model.response.StreamsModel
import com.gprifti.livetv.data.pref.PrefStorage
import com.gprifti.livetv.data.repository.Repository
import com.gprifti.livetv.databinding.FragmentSearchBindingImpl
import com.gprifti.livetv.utils.SnackBar.Companion.snack


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBindingImpl
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var ctx: Context
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        ctx = container!!.context
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        val repository = Repository(LiveTvDatabase(ctx), PrefStorage(ctx))
        val viewModelProviderFactory = SearchProviderFactory(ctx, repository)

        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(SearchViewModel::class.java)
        binding.searchViewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        initRecyclerView()
        changeFilterCategory()
        viewState()
        backPress()
    }

    private fun initRecyclerView() {
        binding.recyclerViewSearchr.layoutManager = LinearLayoutManager(ctx, LinearLayout.VERTICAL, false)
        viewModel.searchResult.observe(viewLifecycleOwner, Observer {
            searchAdapter = SearchAdapter(ctx,navController,it as ArrayList<StreamsModel>)
            binding.recyclerViewSearchr.adapter = searchAdapter
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
                    view?.snack("no intenet ")
                }
                3 -> {
                    binding.includedLayoutView.loadView.visibility = View.VISIBLE
                    binding.includedLayoutLoader.progressBar.visibility = View.INVISIBLE
                    view?.snack("something wrong")
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
