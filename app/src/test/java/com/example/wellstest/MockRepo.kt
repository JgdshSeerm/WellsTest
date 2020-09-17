package com.example.wellstest

import com.example.wellstest.data.Repository
import com.example.wellstest.model.Articles
import com.example.wellstest.model.NewsResponse

class MockRepo : Repository {
    override suspend fun getNews(country: String): NewsResponse {
        val articles = listOf(
            Articles(title = "Fetched 1", description = "Descrption 1"),
            Articles(title = "Fetched 2", description = "Descrption 2")
        )
        return NewsResponse("Success", articles.size, articles)
    }
}