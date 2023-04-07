package com.example.newsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.HeadResponse
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.utilities.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel (
    private val newsRepository:NewsRepository
    ): ViewModel() {
    val allNews:MutableLiveData<Resource<HeadResponse>> = MutableLiveData()
    fun getAllNews()=viewModelScope.launch {
        allNews.postValue(Resource.Loading())
        val response=newsRepository.getAllNews()
        allNews.postValue(handleNewsResponse(response))
    }
    private fun handleNewsResponse(response: Response<HeadResponse>):Resource<HeadResponse>{
        if (response.isSuccessful){
            response.body().let {
                resultResponse->return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}