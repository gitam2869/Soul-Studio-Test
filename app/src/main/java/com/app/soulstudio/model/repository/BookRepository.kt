package com.app.soulstudio.model.repository

import com.app.soulstudio.model.dataclass.Book
import com.app.soulstudio.model.retrofit.APIServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookRepository(private val apiServices: APIServices) {

    private val TAG = "BookRepository"

    fun getBooks(
        bookName: String, startIndex: String, maxResult: String,
        onSuccess: (Book) -> Unit,
        onError: (String) -> Unit
    ) {
        apiServices.getBooks(bookName, startIndex, maxResult).enqueue(object : Callback<Book> {
            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                if (response.isSuccessful && response.body() != null) {
                    Thread {
                        onSuccess(response.body()!!)
                    }.start()
                } else {
                    onError(response.message())
                }
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected exception
             * occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<Book>, t: Throwable) {
                onError("Something went wrong")
            }

        })
    }
}