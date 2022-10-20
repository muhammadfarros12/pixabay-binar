package com.example.pixabay.data.repository

import com.example.pixabay.data.network.datasource.PixabayApiDataSource
import com.example.pixabay.model.SearchResponse
import com.example.pixabay.wrapper.Resource
import java.util.concurrent.Executor

// #2 setelah buat untuk dataSourcenya lanjut ke bagian repository dan implementation (impl)

interface SearchRepository {

    suspend fun searchPhoto(q: String): Resource<SearchResponse>

}

// #3

class SearchRepositoryImpl(private val dataSource: PixabayApiDataSource): SearchRepository{
    /*1
    override suspend fun searchPhoto(q: String): Resource<SearchResponse> {
        return proceed { dataSource.searchPhoto(q) }
    }*/

    // selain menggunakan proceed untuk handle empty bisa juga menjadi berikut


    // 2 : apa bila menggunakan yang dibawah ini maka function proceed sudah tidak diperlukan
    override suspend fun searchPhoto(q: String): Resource<SearchResponse> {
        return try {
            val data = dataSource.searchPhoto(q)
            if (data.posts.isNullOrEmpty()) Resource.Empty() else Resource.Success(data)
        } catch (e: Exception){
            Resource.Error(e)
        }
    }

    // kelemahan dari code ini nantinya apaun hasug statusnya maka akan masuk ke success / error
    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T>{
        return try {
            Resource.Success(coroutine.invoke())
        } catch (e: Exception){
            Resource.Error(e)
        }
    }


}
