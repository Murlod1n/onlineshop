package com.example.onlineshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshop.domain.models.*
import com.example.onlineshop.domain.repository.OnlineShopRepository
import com.example.onlineshop.utils.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class OnlineShopViewModel @Inject constructor(
    private val onlineShopRepository: OnlineShopRepository
) : ViewModel() {

    private val _cart = MutableLiveData(1)
    val cart: LiveData<Int> = _cart

    private val _searchHint = MutableLiveData<HintList>()
    val searchHint: LiveData<HintList> = _searchHint

    private val _selectedProduct = MutableLiveData<SelectedProduct>()
    val selectedProduct: LiveData<SelectedProduct> = _selectedProduct

    private val _currentAccount = MutableLiveData<Account>()
    val currentAccount: LiveData<Account> = _currentAccount

    private val _latestProducts = MutableLiveData<RequestState<LatestItemList>>()
    val latestProducts: LiveData<RequestState<LatestItemList>> = _latestProducts

    private val _flashSaleProducts = MutableLiveData<RequestState<FlashSaleItemList>>()
    val flashSaleProducts: LiveData<RequestState<FlashSaleItemList>> = _flashSaleProducts

    private val _dataIsSuccessful = MutableLiveData(false)
    val dataIsSuccessful: LiveData<Boolean> = _dataIsSuccessful

    private val _selectedProductIsSuccessful = MutableLiveData(false)
    val selectedProductIsSuccessful: LiveData<Boolean> = _selectedProductIsSuccessful

    fun setCurrentAccount(account: Account) {
        _currentAccount.value = account
    }

    fun insertAccount(account: Account) {
        CoroutineScope(Dispatchers.IO).launch {
            onlineShopRepository.insertAccount(account)
        }
    }

    fun updateCart(count: Int) {
        if (count > 0 || _cart.value!! > 1) _cart.value = _cart.value!!.plus(count)
    }

    fun updateSelectedFieldInSelectedProduct() {
        if (_selectedProduct.value != null) {
            val newItem = _selectedProduct.value!!
            newItem.updateSelectedField()
            _selectedProduct.value = newItem
        }
    }

    fun updateAccountImage(uri: String) {

        val account = currentAccount.value

        if (account != null) {
            val updateAccount = Account(
                account.id,
                account.firstName,
                account.lastName,
                account.email,
                uri
            )
            CoroutineScope(Dispatchers.IO).launch { onlineShopRepository.updateAccount(updateAccount) }
            _currentAccount.value = updateAccount
        }
    }

    fun updateSelectedSliderItem(position: Int, isSelected: Boolean) {
        if (_currentAccount.value != null) {
            val newList = _selectedProduct.value!!
            newList.updateImageField(position, isSelected)
            _selectedProduct.value = newList
        }
    }

    fun updateSelectedColor(position: Int, isSelected: Boolean) {
        if (_currentAccount.value != null) {
            val newList = _selectedProduct.value!!
            newList.updateColorField(position, isSelected)
            _selectedProduct.value = newList
        }
    }

    fun checkEmailExists(email: String): Flow<Boolean> =
        onlineShopRepository.checkEmailExists(email)

    fun selectAccountData(firstName: String): Flow<Account> =
        onlineShopRepository.selectAccountData(firstName)

    fun getSelectedProduct() {

        _selectedProductIsSuccessful.value = false

        viewModelScope.launch {
            launch {
                onlineShopRepository.getSelectedProduct().let { response ->
                    if (response.isSuccessful && response.body() != null) {
                        _selectedProduct.value = response.body()
                    }
                }
            }.join()
            _selectedProductIsSuccessful.value = true
        }
    }

    fun getSearchHints() {
        viewModelScope.launch {
            onlineShopRepository.getSearchHint().let { response ->
                if (response.isSuccessful && response.body() != null) {
                    _searchHint.value = response.body()
                }
            }
        }
    }

    fun getProduct() {
        _latestProducts.value = RequestState.Loading
        _flashSaleProducts.value = RequestState.Loading
        _dataIsSuccessful.value = false

        viewModelScope.launch {
            try {
                listOf(
                    launch {
                        delay(1000)
                        onlineShopRepository.getLatestProducts().let { response ->
                            if (response.isSuccessful && response.body() != null) {
                                _latestProducts.value = RequestState.Success(response.body()!!)
                            } else {
                                throw Exception(response.errorBody().toString())
                            }
                        }
                    },
                    launch {
                        onlineShopRepository.getFlashSaleProducts().let { response ->
                            if (response.isSuccessful && response.body() != null) {
                                _flashSaleProducts.value = RequestState.Success(response.body()!!)
                            } else {
                                throw Exception(response.errorBody().toString())
                            }
                        }
                    }
                ).joinAll()
                _dataIsSuccessful.value = true
            } catch (e: Exception) {
                _dataIsSuccessful.value = false
                _latestProducts.value = RequestState.Error(e)
                _flashSaleProducts.value = RequestState.Error(e)
            }
        }
    }
}
