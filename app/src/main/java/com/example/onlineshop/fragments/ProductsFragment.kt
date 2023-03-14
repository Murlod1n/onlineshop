package com.example.onlineshop.fragments

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.service.autofill.*
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.onlineshop.R
import com.example.onlineshop.adapters.ProductsFragmentDelegates
import com.example.onlineshop.databinding.FragmentProductsBinding
import com.example.onlineshop.domain.models.*
import com.example.onlineshop.domain.staticdata.StaticData
import com.example.onlineshop.utils.RequestState
import com.example.onlineshop.viewmodel.OnlineShopViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding
    private val viewModel: OnlineShopViewModel by activityViewModels()
    lateinit var text: String

    private val adapter = ListDelegationAdapter(
        ProductsFragmentDelegates.itemHorizontalListDelegate {
            viewModel.getSelectedProduct()
            findNavController().navigate(R.id.action_productsFragment_to_selectedProductFragment)
        }
    )

    private val loadingAdapter = ListDelegationAdapter(
        ProductsFragmentDelegates.loadingItemHorizontalListDelegate
    )

    private val categoriesItems = StaticData.getCategoriesData()
    private val brandData = StaticData.getBrandItems()
    private val loadingLatestItems = StaticData.getLoadingLatestItems()
    private val loadingFlashSaleItems = StaticData.getLoadingFlashSaleItems()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProductsBinding.inflate(inflater, container, false)

        viewModel.currentAccount.observe(viewLifecycleOwner) { setProfileImage() }

        setTitleColor()
        setProfileImage()
        initRcView()
        initSearchField()

        return binding.root
    }


    private fun setTitleColor() {
        val first = "Trade by "
        val next = "<font color='#4E55D7'>bata</font>"

        requireActivity().findViewById<TextView>(R.id.productsPageTitle).text =
            Html.fromHtml(first + next)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun initSearchField() {
        viewModel.searchHint.observe(viewLifecycleOwner) {
            if (viewModel.searchHint.value?.words != null) {

                val searchFieldAdapter = ArrayAdapter(
                    requireContext(),
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    viewModel.searchHint.value!!.words
                )

                binding.searchEditText.apply {
                    setAdapter(searchFieldAdapter)
                    dismissDropDown()
                    refreshAutoCompleteResults()
                }
            }
        }
        searchFieldListener()
    }


    private fun searchFieldListener() {

        var timer: CountDownTimer? = null

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                timer?.cancel()

                timer = object : CountDownTimer(1500, 1000) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        viewModel.getSearchHints()
                    }
                }.start()
            }
        })
    }


    override fun onResume() {
        super.onResume()

        requireActivity().apply {
            findViewById<AppBarLayout>(R.id.customAppBar).visibility = View.VISIBLE
            findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE

        }
    }


    override fun onStop() {
        super.onStop()
        requireActivity().findViewById<AppBarLayout>(R.id.customAppBar).visibility = View.GONE
    }

    private fun initRcView() {
        viewModel.dataIsSuccessful.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.apply {

                    productsRcView.adapter = adapter

                    if (viewModel.latestProducts.value is RequestState.Success &&
                        viewModel.flashSaleProducts.value is RequestState.Success
                    ) {
                        adapter.apply {
                            items = listOf(
                                CategoryItemList(
                                    title = "",
                                    categoriesItems
                                ),
                                (viewModel.latestProducts.value as RequestState.Success<LatestItemList>).data,
                                (viewModel.flashSaleProducts.value as RequestState.Success<FlashSaleItemList>).data,
                                BrandItemList(
                                    title = "Brands",
                                    brandData
                                )
                            )
                            notifyDataSetChanged()
                        }
                    }
                }
            } else {
                binding.apply {

                    productsRcView.adapter = loadingAdapter

                    if (viewModel.latestProducts.value is RequestState.Loading &&
                        viewModel.flashSaleProducts.value is RequestState.Loading
                    ) {
                        loadingAdapter.apply {
                            items = kotlin.collections.listOf(
                                CategoryItemList(
                                    title = "",
                                    categoriesItems
                                ),
                                LatestItemList(
                                    title = "Latest",
                                    loadingLatestItems
                                ),
                                FlashSaleItemList(
                                    title = "Flash Sale",
                                    loadingFlashSaleItems
                                ),
                                BrandItemList(
                                    title = "Brands",
                                    brandData
                                )
                            )
                            notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }

    private fun setProfileImage() {
        if (viewModel.currentAccount.value?.imageUri != "") {
            requireActivity().findViewById<ImageView>(R.id.appBarProfImage)
                .setImageURI(Uri.parse(viewModel.currentAccount.value!!.imageUri))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProductsFragment()
    }
}

