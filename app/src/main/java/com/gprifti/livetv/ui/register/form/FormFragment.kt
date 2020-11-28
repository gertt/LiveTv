package com.gprifti.livetv.ui.register.form

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.gprifti.livetv.databinding.FragmentFormBinding
import com.gprifti.livetv.utils.SnackBar.Companion.snack


class FormFragment : Fragment() {

    private lateinit var binding: FragmentFormBinding
    private lateinit var viewModel: FormViewModel
    private lateinit var ctx: Context
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        ctx = container!!.context
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_form, container, false)
        val repository = Repository(LiveTvDatabase(ctx), PrefStorage(ctx))
        val viewModelProviderFactory = FormProviderFactory(ctx, repository)

        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(FormViewModel::class.java)
        binding.formViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        changeView()
        preSetEmail()
        viewState()
        backPress()
    }

    private fun changeView() {
        viewModel.validateForm.observe(viewLifecycleOwner, Observer { stateForm ->
            when (stateForm.id) {
                0, 1, 2, 3, 4 -> view?.snack(getString(R.string.snack_txt_form, stateForm.field))
            }
        })
    }

    private fun preSetEmail() {
        viewModel.preSetEmail.observe(viewLifecycleOwner, Observer { email ->
            binding.txtEmail.setText(email)
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
                    navController.navigate(R.id.action_formFragment_to_nav_graph_bottom)
                }
            }
        })
    }

    private fun backPress() {
        viewModel.backButton.observe(viewLifecycleOwner, Observer { backButton ->
            if (backButton) {
                navController.navigateUp()
            }
        })
    }
}