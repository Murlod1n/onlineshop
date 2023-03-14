package com.example.onlineshop.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.onlineshop.R
import com.example.onlineshop.domain.models.Account
import com.example.onlineshop.databinding.FragmentSignInBinding
import com.example.onlineshop.viewmodel.OnlineShopViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.job
import kotlin.coroutines.coroutineContext

@AndroidEntryPoint
class SignInFragment : Fragment() {

    lateinit var binding: FragmentSignInBinding
    private val viewModel: OnlineShopViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        formValidationListener()
        formSubmit()
        binding.logInLink.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_logInFragment)
        }

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility =
            View.GONE

        binding.apply {
            firstNameContainer.helperText = null
            lastNameContainer.helperText = null
            emailContainer.helperText = null
        }
    }

    private fun formSubmit() {
        binding.apply {
            signInButton.setOnClickListener {

                firstNameContainer.helperText = validFirstName()
                lastNameContainer.helperText = validLastName()
                emailContainer.helperText = validEmail()

                if (
                    firstNameContainer.helperText == null &&
                    lastNameContainer.helperText == null &&
                    emailContainer.helperText == null
                ) {

                    val flow = viewModel.checkEmailExists(emailEditText.text.toString())

                    flow.onEach {
                        if (!it) {
                            viewModel.insertAccount(
                                Account(
                                    firstName = firstNameEditText.text.toString(),
                                    lastName = lastNameEditText.text.toString(),
                                    email = emailEditText.text.toString()
                                )
                            )
                            coroutineContext.job.cancel()
                            Toast.makeText(
                                requireContext(),
                                "Registration completed successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            emailContainer.helperText = "Such a user is already registered"
                        }
                    }.launchIn(lifecycleScope)

                }
            }
        }
    }

    private fun formValidationListener() {
        firstNameListener()
        lastNameListener()
        emailListener()
    }

    private fun firstNameListener() {
        binding.firstNameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.firstNameContainer.helperText = validFirstName()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun lastNameListener() {
        binding.lastNameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.lastNameContainer.helperText = validLastName()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun emailListener() {
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.emailContainer.helperText = validEmail()
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

    private fun validLastName(): String? {
        val lastNameText = binding.lastNameEditText.text.toString()
        if (lastNameText.isEmpty()) {
            return "The field cannot be empty"
        }
        return null
    }

    private fun validEmail(): String? {
        val emailText = binding.emailEditText.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid email address"
        }
        return null
    }


    companion object {
        @JvmStatic
        fun newInstance() = SignInFragment()
    }
}