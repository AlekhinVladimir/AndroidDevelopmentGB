package com.example.m16_architecture.data


import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://www.boredapi.com"


interface UsefulActivityDataSource {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("api/activity")
    suspend fun getUsefulActivity(): Response<UsefulActivityDto>
}

object RetrofitInstance {
    private val retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    val usefulActivityDataSource = retrofit.create(UsefulActivityDataSource::class.java)
}
