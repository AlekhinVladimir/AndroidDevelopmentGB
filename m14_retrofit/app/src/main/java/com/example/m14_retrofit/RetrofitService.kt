package com.example.m14_retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface RetrofitService {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("/api/")
    fun getImageList(): Call<RandomUser>
}