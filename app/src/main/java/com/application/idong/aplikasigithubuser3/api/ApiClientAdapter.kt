package com.application.idong.aplikasigithubuser3.api

import android.content.Context
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by ridhopratama on 17,October,2020
 */

class ApiClientAdapter {
    private val gsonConverterFactory: GsonConverterFactory = GsonConverterFactory.create(Gson())
    var context: Context
    var apiBaseUrl: String =
        UriService.BASEURL
    var timeout:Long = 90
    var timeUnit: TimeUnit = TimeUnit.SECONDS
    var isNeedToken: Boolean = true

    constructor(context: Context) {
        this.context = context
    }

    private fun okHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder().apply {
             addInterceptor(interceptor)
            .connectTimeout(timeout, timeUnit)
            .readTimeout(timeout, timeUnit)
            .addInterceptor(InterceptorApi())
            if (isNeedToken)
                authenticator(InterceptorApi())
        }

        return builder.build()
    }

    private fun retrofitInstance(): Retrofit {
        return Retrofit.Builder()
                        .baseUrl(apiBaseUrl)
                        .addConverterFactory(gsonConverterFactory)
                        .client(okHttpClient())
                        .build()
    }

    fun getUserService(): UserService {
        return retrofitInstance().create(UserService::class.java)
    }
}