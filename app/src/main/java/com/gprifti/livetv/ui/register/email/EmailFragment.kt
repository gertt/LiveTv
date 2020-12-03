package com.gprifti.livetv.ui.register.email

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
import com.gprifti.livetv.databinding.FragmentEmailBinding
import com.gprifti.livetv.utils.SnackBar.Companion.snack



class EmailFragment : Fragment() {

    private lateinit var binding: FragmentEmailBinding
    private lateinit var viewModel: EmailViewModel
    private lateinit var navController: NavController
    private lateinit var ctx: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        ctx = container!!.context
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_email, container, false)
        val repository = Repository(LiveTvDatabase(ctx), PrefStorage(ctx))
        val viewModelProviderFactory = EmailProviderFactory(repository)

        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(EmailViewModel::class.java)
        binding.emailViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        changeView(view)
        preSetEmail()
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