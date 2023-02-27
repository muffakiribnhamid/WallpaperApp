package com.example.wallpaperapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
const val BASE_URL2 ="https://api.unsplash.com/search/photos/"
object RetrofitInstance2 {
    private val retrofit2 by lazy {
        Retrofit.Builder().baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiInterface2 by lazy {
        retrofit2.create(ApiInterface2::class.java)
    }
}