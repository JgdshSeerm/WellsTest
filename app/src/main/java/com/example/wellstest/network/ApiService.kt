package com.example.wellstest.network

import com.example.wellstest.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/v2/top-headlines")
    suspend fun getNews(@Query("country") country: String, @Query("apiKey") apiKey: String): Response<NewsResponse>
}