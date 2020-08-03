package com.gnova.bakingapp_kotlin.di.modules

import com.gnova.bakingapp_kotlin.api.BakingApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class ApiModule {

    @Provides
    @Reusable
    fun providesRetrofit(
        okHttpClient: OkHttpClient.Builder): BakingApi =
        Retrofit.Builder()
            .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
            .client(
                okHttpClient
                    .build()
            )
            .addConverterFactory( MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // Needed for Rx
            .build()
            .create(BakingApi::class.java)

    @Provides
    @Reusable
    fun providesOkHttpClient(): OkHttpClient.Builder =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

}