package com.example.onlineshop.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentProfileBinding
import com.example.onlineshop.viewmodel.OnlineShopViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: OnlineShopViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        initProfileFragment()

        return binding.root
    }

    private fun initProfileFragment() {
        binding.apply {
            backBtn.setOnClickListener { findNavController().popBackStack() }
            logOutItem.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_signInFragment)
                requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE
            }
            changePhotoBtn.setOnClickListener { openImage() }
            viewModel.currentAccount.observe(viewLifecycleOwner) { setProfileImage() }
            setProfileImage()
        }
    }

    private fun setProfileImage() {
        if (viewModel.currentAccount.value?.imageUri != "" ) {
            binding.profileImage.setImageURI(Uri.parse(viewModel.currentAccount.value!!.imageUri))
        }
    }

    private fun openImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==100 && resultCode == RESULT_OK){
            val uri = data?.data
            uri?.let {
                requireActivity().contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            }
            viewModel.updateAccountImage(uri.toString())
            binding.profileImage.setImageURI(uri)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}