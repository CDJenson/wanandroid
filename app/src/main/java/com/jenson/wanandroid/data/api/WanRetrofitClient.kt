package com.jenson.wanandroid.data.api

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.jenson.core.data.base.BaseRetrofitClient
import com.jenson.wanandroid.App
import com.jenson.wanandroid.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *  author: CDJenson
 *  date: 2020/8/13 14:14
 *	version: 1.0
 *  description: One-sentence description
 */

class WanRetrofitClient private constructor() : BaseRetrofitClient() {

    val loggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
        }
    }

    val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.CONTEXT))
    }

    override fun initOkHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        return builder.apply {
            addInterceptor(loggingInterceptor)
            cookieJar(cookieJar)
            connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
        }
    }

    override fun initRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
        return builder.apply {
            addConverterFactory(GsonConverterFactory.create())
        }
    }

    companion object {
        const val TIME_OUT = 5
        val INSTANCE: WanRetrofitClient by lazy {
            WanRetrofitClient()
        }
    }
}