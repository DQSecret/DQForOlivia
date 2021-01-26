package com.example.dqddu.coroutines.example.retrofit

import com.example.dqddu.coroutines.example.api.ApiService
import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Retrofit 相关
 *
 * @param {参数说明}
 *
 * @author DQDana For Olivia
 * @since 2020/12/3 4:56 PM
 */

object RetrofitExt {

    private val mHttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor { message ->
            Logger.d(message)
        }.apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    private val mOkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            /*.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))*/
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(mOkHttpClient)
            .baseUrl("https://www.wanandroid.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val mApiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
