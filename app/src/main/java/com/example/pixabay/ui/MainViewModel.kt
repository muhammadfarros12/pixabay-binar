package com.example.pixabay.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabay.model.SearchResponse
import com.example.pixabay.data.network.service.PixabayApiService
import com.example.pixabay.data.repository.SearchRepository
import com.example.pixabay.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: SearchRepository) : ViewModel() {

    val searchResult = MutableLiveData<Resource<SearchResponse>>()

    fun searchPost(query: String) {
        searchResult.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.searchPhoto(query)
            viewModelScope.launch(Dispatchers.Main) {
                searchResult.postValue(data)
            }
        }

    }

}