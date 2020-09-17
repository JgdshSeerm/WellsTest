package com.example.wellstest.data

import com.example.wellstest.model.NewsResponse

interface Repository {
   // fun getNews(country: String): LiveData<ApiResult<NewsResponse?>>
    suspend fun getNews(country: String): NewsResponse
}