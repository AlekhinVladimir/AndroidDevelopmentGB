package com.example.m14_retrofit

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://randomuser.me/"

class RetrofitBackend {
    object RetrofitInstance {
        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val searchImageApi: SearchImageApi = retrofit.create(
            SearchImageApi::class.java
        )
    }
    interface SearchImageApi {

        //        @GET()
//        fun getImage(@Query("limit") limit: Int = 1): Call<List<Image>>
        @Headers(
            "Accept: application/json",
            "Content-type: application/json"
        )
        @GET("/api/")
        fun getImageList(): Call<RandomUser>
    }
}
