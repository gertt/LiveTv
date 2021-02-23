package com.gprifti.livetv.ui.register.email

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.gprifti.livetv.R
import com.gprifti.livetv.databinding.FragmentEmailBinding
import com.gprifti.livetv.utils.SnackBar.Companion.snack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailFragment : Fragment(R.layout.fragment_email) {

    private lateinit var binding: FragmentEmailBinding
    private lateinit var navController: NavController
    private val viewModel: EmailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEmailBinding.bind(view)
        navController = Navigation.findNavController(view)

        setListener()
        changeView(view)
        preSetEmail()
    }

    private fun setListener(){
        binding.nextButton.setOnClickListener {
            viewModel.clickNextButton(binding.emailEdx.text.toString())
        }
    }

    private fun changeView(view: View) {
        viewModel.validateEmail.observe(viewLifecycleOwner, Observer { it ->
                it.getContentIfNotHandled()?.let {
                    when(it.id){
                        0 -> navController.navigate(R.id.action_emailFragment_to_formFragment)
                        1 -> view.snack(getString(R.string.snack_txt_form, it.field))
                    }
                }
        })
    }

    private fun preSetEmail() {
        viewModel.setEmail.observe(viewLifecycleOwner, Observer { email ->
            binding.emailEdx.setText(email)
        })
    }
}