package com.example.onlineshop.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentLogInBinding
import com.example.onlineshop.viewmodel.OnlineShopViewModel
import kotlinx.coroutines.launch


class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private val viewModel: OnlineShopViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater, container, false)

        formValidationListener()
        formSubmit()

        return binding.root
    }


    private fun formSubmit() {
        binding.apply {
            logInBtn.setOnClickListener {

                editTextFirstNameLogInContainer.helperText = validFirstName()
                editTextPasswordLogInContainer.helperText = validPassword()

                if (
                    editTextFirstNameLogInContainer.helperText == null &&
                    editTextPasswordLogInContainer.helperText == null
                ) {

                    val flow = viewModel.selectAccountData(firstNameEditText.text.toString())

                    lifecycleScope.launch {
                        flow.collect {
                            if (it != null) {
                                viewModel.getProduct()
                                viewModel.setCurrentAccount(it)
                                findNavController().navigate(R.id.action_logInFragment_to_productsFragment)
                            } else {
                                editTextFirstNameLogInContainer.helperText =
                                    "There is no user with this name"
                            }
                        }
                    }
                }
            }
        }
    }

    private fun formValidationListener() {
        firstNameListener()
        passwordListener()
    }

    private fun firstNameListener() {
        binding.firstNameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.editTextFirstNameLogInContainer.helperText = validFirstName()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun passwordListener() {
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.editTextPasswordLogInContainer.helperText = validPassword()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }


    private fun validFirstName(): String? {
        val fistNameText = binding.firstNameEditText.text.toString()
        if (fistNameText.isEmpty()) {
            return "The field cannot be empty"
        }
        return null
    }

    private fun validPassword(): String? {
        val lastNameText = binding.passwordEditText.text.toString()
        if (lastNameText.isEmpty()) {
            return "The field cannot be empty"
        }
        return null
    }


    companion object {
        @JvmStatic
        fun newInstance() = LogInFragment()
    }
}