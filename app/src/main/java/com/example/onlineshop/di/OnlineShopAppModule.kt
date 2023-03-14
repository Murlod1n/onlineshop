package com.example.onlineshop.di

import android.app.Application
import com.example.onlineshop.domain.api.OnlineShopApiService
import com.example.onlineshop.domain.dao.OnlineShopDao
import com.example.onlineshop.domain.database.OnlineShopDataBase
import com.example.onlineshop.domain.repository.OnlineShopRepository
import com.example.onlineshop.utils.Constants
import com.example.onlineshop.viewmodel.OnlineShopViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object OnlineShopAppModule {

    @Singleton
    @Provides
    fun providerOnlineShopRepository(
        onlineShopDao: OnlineShopDao,
        onlineShopApiService: OnlineShopApiService
    ): OnlineShopRepository {
        return OnlineShopRepository(onlineShopDao, onlineShopApiService)
    }

    @Singleton
    @Provides
    fun providerShopOnlineDataBase(app: Application): OnlineShopDataBase {
        return OnlineShopDataBase.getDatabase(context = app)
    }

    @Singleton
    @Provides
    fun providerOnlineShopDao(onlineShopDataBase: OnlineShopDataBase): OnlineShopDao {
        return onlineShopDataBase.todoDao()
    }

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideRetrofitInstance(BASE_URL: String): OnlineShopApiService {
        val contentType = MediaType.get("application/json")
        val format = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(format.asConverterFactory(contentType))
            .build()
            .create(OnlineShopApiService::class.java)

    }
}