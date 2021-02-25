package com.gprifti.livetv.ui.register.form

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.gprifti.livetv.R
import com.gprifti.livetv.databinding.FragmentFormBinding
import com.gprifti.livetv.utils.SnackBar.Companion.snack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.M)
class FormFragment : Fragment(R.layout.fragment_form) {

    private lateinit var binding: FragmentFormBinding
    private lateinit var navController: NavController
    private  val viewModel: FormViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFormBinding.bind(view)
        navController = Navigation.findNavController(view)

        setListener()
        changeView()
        preSetEmail()
        viewState()
        backPress()
    }

    private fun setListener(){
        binding.nextFormButton.setOnClickListener {
            viewModel.clickNextButtonForm(
                binding.txtUsername.text.toString(),
                binding.txtEmail.text.toString(),
                binding.txtPass.text.toString(),
                binding.txtSurname.text.toString(),
                binding.txtPhone.text.toString())
        }
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