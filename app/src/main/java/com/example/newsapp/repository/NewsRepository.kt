package com.example.newsapp.repository

import com.example.newsapp.remote.RetrofitInstance


class NewsRepository {
    suspend fun getAllNews()=RetrofitInstance.api.getAllNews()
}