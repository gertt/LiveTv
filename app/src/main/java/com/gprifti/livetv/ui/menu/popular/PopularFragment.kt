package com.gprifti.livetv.ui.menu.popular

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gprifti.livetv.R
import com.gprifti.livetv.data.db.LiveTvDatabase
import com.gprifti.livetv.data.pref.PrefStorage
import com.gprifti.livetv.data.repository.Repository
import com.gprifti.livetv.databinding.FragmentPopularBinding
import com.gprifti.livetv.utils.SnackBar.Companion.snack



class PopularFragment : Fragment() {


    private lateinit var binding: FragmentPopularBinding
    private lateinit var viewModel: PopularViewModel
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var ctx: Context


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        ctx = container!!.context
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_popular, container, false)
        val repository = Repository(LiveTvDatabase(ctx), PrefStorage(ctx))
        val viewModelProviderFactory = PopularProviderFactory(ctx, repository)

        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(PopularViewModel::class.java)
        binding.popularViewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewState()
        backPress()
    }

    @SuppressLint("WrongConstant")
    private fun initRecyclerView() {
        binding.recyclerViewPopular.layoutManager = LinearLayoutManager(ctx, LinearLayout.VERTICAL, false)
        binding.recyclerViewPopular.layoutManager = GridLayoutManager(ctx, 3)
        viewModel.searchResult.observe(viewLifecycleOwner, Observer {
            popularAdapter = PopularAdapter(it)
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
                    activity?.finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}
