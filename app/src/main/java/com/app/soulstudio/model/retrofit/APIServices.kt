package com.app.soulstudio.model.retrofit

import com.app.soulstudio.common.Constants
import com.app.soulstudio.model.dataclass.Book
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIServices {

    @GET(Constants.API_GET_BOOKS)
    fun getBooks(
        @Query("q") flowers: String,
        @Query("startIndex") startIndex: String,
        @Query("maxResults") maxResults: String
    ): Call<Book>
}