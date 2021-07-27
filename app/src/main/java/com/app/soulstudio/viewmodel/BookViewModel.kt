package com.app.soulstudio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.soulstudio.model.dataclass.Book
import com.app.soulstudio.model.repository.BookRepository

class BookViewModel(private val bookRepository: BookRepository) : ViewModel() {

    private val _bookResponse = MutableLiveData<Book>()
    val bookResponse: LiveData<Book>
        get() = _bookResponse


    private val _errorResponse = MutableLiveData<String>()
    val errorResponse: LiveData<String>
        get() = _errorResponse

    fun getBook(bookName: String, startIndex: String, maxResult: String) {
        bookRepository.getBooks(
            bookName,
            startIndex,
            maxResult,
            {
                _bookResponse.postValue(it)
            }, {
                _errorResponse.postValue(it)
            })
    }
}

class BookViewModelFactory(private val bookRepository: BookRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookViewModel(bookRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}