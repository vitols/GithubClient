package com.example.android.githubclient.base.api

import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by admin on 22.02.2018.
 */
object RestApi {
    private val URL = "https://api.github.com"
    private var retrofit: Retrofit? = null

    fun init(authenticator: Authenticator) {

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(Interceptor())
                .authenticator(authenticator)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addNetworkInterceptor(interceptor)

        retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(URL)
                .client(okHttpClient.build())
                .build()
    }

    fun <T> createService(serviceClass: Class<T>) : T {
        if (retrofit == null) {
            throw IllegalStateException("`RestApi.init(Authenticator)` before calling this method.")
        }
        return retrofit!!.create(serviceClass)
    }


}