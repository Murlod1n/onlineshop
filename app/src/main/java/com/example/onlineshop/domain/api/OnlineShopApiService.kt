package com.example.onlineshop.domain.api

import com.example.onlineshop.domain.models.FlashSaleItemList
import com.example.onlineshop.domain.models.HintList
import com.example.onlineshop.domain.models.LatestItemList
import com.example.onlineshop.domain.models.SelectedProduct
import retrofit2.Response
import retrofit2.http.GET

interface OnlineShopApiService {

    @GET("cc0071a1-f06e-48fa-9e90-b1c2a61eaca7")
    suspend fun getLatestProducts(): Response<LatestItemList>

    @GET("a9ceeb6e-416d-4352-bde6-2203416576ac")
    suspend fun getFlashSaleProducts(): Response<FlashSaleItemList>

    @GET("f7f99d04-4971-45d5-92e0-70333383c239")
    suspend fun getSelectedProduct(): Response<SelectedProduct>

    @GET("4c9cd822-9479-4509-803d-63197e5a9e19")
    suspend fun getSearchHint(): Response<HintList>

}