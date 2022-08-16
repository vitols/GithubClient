package com.example.android.githubclient.base.api

import android.util.Log
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.controllers.LoginController
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by admin on 22.02.2018.
 */
object RestApi {
    private var retrofit: Retrofit? = null

    fun init(authenticator: Authenticator) {

        val okHttpClient = OkHttpClient.Builder()
                /*.addInterceptor(Interceptor())
                .authenticator(authenticator)*/

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addNetworkInterceptor(interceptor)

        okHttpClient.addInterceptor(object: Interceptor {
            override fun intercept(chain: Interceptor.Chain?): Response {
                var original = chain?.request();

                var requestBuilder = original
                        ?.newBuilder()
                        ?.addHeader("Authorization", "token " + LoginController.instance.accessToken)
                        ?.build();
                try {
                    return chain!!.proceed(requestBuilder);
                } catch (e: Exception) {
                    Log.e("RestApi", e.message!!)
                    return chain!!.proceed(requestBuilder)
                }
            }
        })

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