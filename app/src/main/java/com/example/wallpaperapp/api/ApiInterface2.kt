package com.example.wallpaperapp.api

import com.example.wallpaperapp.SearchResponseModel
import com.example.wallpaperapp.model.PhotoResponse
import com.example.wallpaperapp.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface2 {
    @GET("?client_id=rBtiHTgGsN5p45mLbfl7I9SE7PD7uUXTMiT4aMQwMlM")
    fun searchData(@Query("per_page")per_page : Int,@Query("page")page : Int,@Query("query")query:String) : Call<SearchResponseModel>
}