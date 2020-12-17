package com.jenson.core.data.base

import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 *  author: CDJenson
 *  date: 2020/8/13 14:32
 *	version: 1.0
 *  description: One-sentence description
 */
abstract class BaseRetrofitClient {

    private val okHttpClient: OkHttpClient
        get() {
            return initOkHttpClientBuilder(OkHttpClient.Builder()).build()
        }

    abstract fun initOkHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder

    abstract fun initRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder

    fun <T> getService(serviceClass: Class<T>,baseUrl:String): T {
        return initRetrofitBuilder(Retrofit.Builder())
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .build()
            .create(serviceClass)
    }
}