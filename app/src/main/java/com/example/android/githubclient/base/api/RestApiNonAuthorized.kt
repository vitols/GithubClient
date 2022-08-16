package com.example.android.githubclient.base.api

import com.example.android.githubclient.base.ConstValues
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by admin on 22.02.2018.
 */
object RestApiNonAuthorized {
    private var retrofit: Retrofit? = null

    fun init() {

        val okHttpClient = OkHttpClient.Builder()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addNetworkInterceptor(interceptor)

        retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ConstValues.Urls.BASE_API_URL)
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