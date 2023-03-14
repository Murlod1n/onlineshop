package com.example.onlineshop.fragments

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.onlineshop.R
import com.example.onlineshop.adapters.ColorRcViewAdapter
import com.example.onlineshop.adapters.ImageSliderRcViewAdapter
import com.example.onlineshop.adapters.ImageViewPagerAdapter
import com.example.onlineshop.databinding.FragmentSelectedProductBinding
import com.example.onlineshop.viewmodel.OnlineShopViewModel


class SelectedProductFragment : Fragment() {

    private lateinit var binding: FragmentSelectedProductBinding
    private val viewModel: OnlineShopViewModel by activityViewModels()

    private lateinit var imageViewPagerAdapter: ImageViewPagerAdapter
    private lateinit var imageSliderRcViewAdapter: ImageSliderRcViewAdapter
    private lateinit var colorRcViewAdapter: ColorRcViewAdapter

    var curId = 0
    private var selectedColorId = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectedProductBinding.inflate(inflater, container, false)

        initSelectedProduct()


        return binding.root
    }

    private fun initSelectedProduct() {
        viewModel.selectedProductIsSuccessful.observe(viewLifecycleOwner) {
            if (viewModel.selectedProduct.value != null && viewModel.selectedProductIsSuccessful.value == true) {
                binding.apply {

                    productTitle.text = viewModel.selectedProduct.value!!.name
                    productPrice.text = requireActivity().getString(
                        R.string.price_format,
                        viewModel.selectedProduct.value!!.price.toString()
                    )
                    productDescription.text = viewModel.selectedProduct.value!!.description
                    rating.text = viewModel.selectedProduct.value!!.rating.toString()
                    reviews.text = viewModel.selectedProduct.value!!.numberOfReviews.toString()

                    backBtn.setOnClickListener { findNavController().popBackStack() }
                    plusBtn.setOnClickListener { viewModel.updateCart(1) }
                    minusBtn.setOnClickListener { viewModel.updateCart(-1) }
                    favBtn.setOnClickListener { viewModel.updateSelectedFieldInSelectedProduct() }
                    addToCartBtn.setOnClickListener {
                        Toast.makeText(
                            requireContext(),
                            "The product has been added to the cart",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    viewModel.cart.observe(viewLifecycleOwner) {
                        cartCount.text = viewModel.cart.value!!.toString()
                        allPrice.text = requireActivity().getString(
                            R.string.price_btn_format,
                            (viewModel.cart.value!! * viewModel.selectedProduct.value!!.price).toString()
                        )
                    }
                }
                setUpViewPager()
            }
        }

        viewModel.selectedProduct.observe(viewLifecycleOwner) {
            if (viewModel.selectedProduct.value != null) {

                setUpRcView()
                setColorRcView()

                if (viewModel.selectedProduct.value!!.isSelected) {
                    binding.favBtn.setImageResource(R.drawable.baseline_favorite_24)
                } else {
                    binding.favBtn.setImageResource(R.drawable.baseline_favorite_border_24)
                }
            }
        }
    }

    private fun setSliderImage(position: Int) {
        binding.largeImage.currentItem = position
    }

    private fun updateColorSelect(position: Int) {
        viewModel.updateSelectedColor(position, true)
        viewModel.updateSelectedColor(selectedColorId, false)
        selectedColorId = position
    }

    private fun setColorRcView() {
        colorRcViewAdapter = ColorRcViewAdapter(viewModel.selectedProduct.value!!.colors) {
            updateColorSelect(it)
        }
        binding.colorRcView.adapter = colorRcViewAdapter
    }

    private fun setUpRcView() {
        imageSliderRcViewAdapter =
            ImageSliderRcViewAdapter(viewModel.selectedProduct.value!!.imagesUrls) {
                setSliderImage(it)
            }

        binding.rcView.adapter = imageSliderRcViewAdapter
    }

    private fun setUpViewPager() {
        imageViewPagerAdapter =
            ImageViewPagerAdapter(viewModel.selectedProduct.value!!.imagesUrls)

        binding.largeImage.apply {
            adapter = imageViewPagerAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            val currentPageIndex = 0
            currentItem = currentPageIndex
            registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        viewModel.updateSelectedSliderItem(curId, false)
                        curId = position
                        viewModel.updateSelectedSliderItem(position, true)
                    }
                }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        binding.largeImage.unregisterOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {}
        )
    }


    companion object {
        @JvmStatic
        fun newInstance() = SelectedProductFragment()
    }
}