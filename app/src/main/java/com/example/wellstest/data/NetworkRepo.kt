package com.example.wellstest.data

import com.example.wellstest.model.NewsResponse
import com.example.wellstest.network.ApiWrapper
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

class NetworkRepo : Repository {

    override suspend fun getNews(country: String): NewsResponse {
        return processRequest {
            ApiWrapper.apiService.getNews(country, "80e27ad0404d476587a3619722680148")
        }
    }


//    suspend fun getNews(country: String): NewsResponse {
//
//        val resultLiveData = MutableLiveData<ApiResult<NewsResponse?>>()
//        resultLiveData.value = ApiResult.Loading
//        ApiWrapper.apiService.getNews(country, "80e27ad0404d476587a3619722680148")
//            .enqueue(object : Callback<NewsResponse> {
//                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
//                    resultLiveData.value = t.message?.let { ApiResult.Error(it) }
//                }
//
//                override fun onResponse(
//                    call: Call<NewsResponse>,
//                    newsResponse: retrofit2.Response<NewsResponse>
//                ) {
//                    resultLiveData.value = ApiResult.Success(newsResponse.body())
//                }
//            })
//        return resultLiveData
//
//    }

    private suspend fun <T : Any> processRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val message = StringBuilder()
            val error = response.errorBody().toString()
            error.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                } catch (e: JSONException) {
                    message.append("\n")
                }

            }
            message.append("\nError code ${response.code()}")
            throw IOException(message.toString())
        }
    }

}