package com.example.pixabay.provider

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.pixabay.data.network.datasource.PixabayApiDataSource
import com.example.pixabay.data.network.datasource.PixabayApiDataSourceImpl
import com.example.pixabay.data.network.service.PixabayApiService
import com.example.pixabay.data.repository.SearchRepository
import com.example.pixabay.data.repository.SearchRepositoryImpl
import com.example.pixabay.model.SearchResponse

object ServiceLocator {

    // diawali dengan provide dari API
    fun provideChucker(appContext: Context): ChuckerInterceptor{
        return ChuckerInterceptor.Builder(appContext).build()
        // builder pattern
    }

    // provide untuk apiService
    fun provideApiService(chuckerInterceptor: ChuckerInterceptor): PixabayApiService{
        return PixabayApiService.invoke(chuckerInterceptor)
    }

    // provide datasource
    fun provideDataSource(apiService: PixabayApiService): PixabayApiDataSource{
        return PixabayApiDataSourceImpl(apiService)
    }

    // fungsi ini yang bakal digunakan di luar yang mana akan memetakan apa aja yang kita butuhkan
    // karena ini adalah bentuk dari pengganti dari DI ( koin, dagger, hilt)
    fun provideRepository(context: Context): SearchRepository{
        return SearchRepositoryImpl(provideDataSource(provideApiService(provideChucker(context))))
    }



}