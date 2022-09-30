package com.example.pixabay

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabay.model.SearchResponse
import com.example.pixabay.service.PixabayApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val apiService: PixabayApiService by lazy {
        PixabayApiService.invoke()
    }

    val searchResult = MutableLiveData<SearchResponse>()
    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Exception>()

    fun searchPost(query: String) {
        loadingState.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = apiService.searchPhoto(query = query)
                viewModelScope.launch(Dispatchers.Main) {
                    searchResult.postValue(data)
                    loadingState.postValue(false)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    loadingState.postValue(false)
                    errorState.postValue(e)
                }
            }
        }

    }

}