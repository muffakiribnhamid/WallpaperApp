package com.example.wallpaperapp.api
import com.example.wallpaperapp.model.PhotoResponse
import com.example.wallpaperapp.model.Urls
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("?client_id=rBtiHTgGsN5p45mLbfl7I9SE7PD7uUXTMiT4aMQwMlM")
    fun getData(@Query("per_page")per_page : Int,@Query("page")page : Int) : Call<PhotoResponse>

}
