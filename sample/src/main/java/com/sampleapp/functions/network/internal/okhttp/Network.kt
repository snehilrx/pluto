package com.sampleapp.functions.network.internal.okhttp

import com.pluto.plugins.network.okhttp.addPlutoOkhttpInterceptor
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Network {

    private const val READ_TIMEOUT = 30L

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.mocklets.com/p68296/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptors()
        .build()

    fun <T> getService(cls: Class<T>): T {
        return retrofit.create(cls)
    }

    inline fun <reified T> getService(): Lazy<T> {
        return lazy {
            getService(T::class.java)
        }
    }
}

private fun OkHttpClient.Builder.addInterceptors(): OkHttpClient.Builder {
//    addInterceptor(GzipRequestInterceptor())
    addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    addPlutoOkhttpInterceptor()
    return this
}
