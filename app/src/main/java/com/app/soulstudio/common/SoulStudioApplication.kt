package com.app.soulstudio.common

import android.app.Application
import android.content.Context
import com.app.soulstudio.model.repository.BookRepository
import com.app.soulstudio.model.retrofit.RetrofitClientBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class SoulStudioApplication : Application() {

    private val TAG = "SoulStudioApplication"

    val bookRepository by lazy { BookRepository(RetrofitClientBuilder.buildAPIService()) }

    init {
        instance = this
    }

    companion object {
        private var instance: SoulStudioApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context: Context = SoulStudioApplication.applicationContext()
    }

}