package com.example.onlineshop.domain.repository

import com.example.onlineshop.domain.api.OnlineShopApiService
import com.example.onlineshop.domain.dao.OnlineShopDao
import com.example.onlineshop.domain.models.Account
import kotlinx.coroutines.flow.Flow


class OnlineShopRepository(
    private val onlineShopDao: OnlineShopDao,
    private val onlineShopApiService: OnlineShopApiService
) {
    suspend fun getLatestProducts() = onlineShopApiService.getLatestProducts()

    suspend fun getFlashSaleProducts() = onlineShopApiService.getFlashSaleProducts()

    suspend fun getSelectedProduct() = onlineShopApiService.getSelectedProduct()

    suspend fun getSearchHint() = onlineShopApiService.getSearchHint()

    suspend fun insertAccount(account: Account) = onlineShopDao.insetAccount(account)

    suspend fun updateAccount(account: Account) = onlineShopDao.updateAccount(account)

    fun selectAccountData(firstName: String): Flow<Account> =
        onlineShopDao.selectAccountData(firstName)

    fun checkEmailExists(email: String): Flow<Boolean> =
        onlineShopDao.checkEmailExists(email)

}