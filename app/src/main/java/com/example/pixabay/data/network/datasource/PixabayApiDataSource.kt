package com.example.pixabay.data.network.datasource

import com.example.pixabay.data.network.service.PixabayApiService
import com.example.pixabay.model.SearchResponse

// #1 persiapan level data untuk clean code & dibuat setelah selesai membuat service

/*
* ketika ingin mengganti API lain, maka kita akan mengganti tiap API service (PixabayApiService)
* dan interface ini digunakan untuk mencegah hal tersebut dan hanya menggunakan / menggunakan impelmentor (impl_
* */

interface PixabayApiDataSource {
    // berdasarkan dari function yang ada di service
    suspend fun searchPhoto(query: String): SearchResponse
}

/*
* kegunaan class impl(implementor) agar less depend (tergantung) dengan class service
*
* menggunakan SOLID principal nomor 3 (Liskov subtitution) & 5 (dependency inversion)
* */

class PixabayApiDataSourceImpl(private val api: PixabayApiService): PixabayApiDataSource{
    override suspend fun searchPhoto(query: String): SearchResponse {
        return api.searchPhoto(query)
    }
}