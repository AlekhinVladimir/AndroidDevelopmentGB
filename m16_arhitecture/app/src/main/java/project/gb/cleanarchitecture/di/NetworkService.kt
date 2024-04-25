package project.gb.cleanarchitecture.di

import project.gb.cleanarchitecture.data.UsefulActivityDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://www.boredapi.com"

interface BoredApiService {
    @GET("/api/activity/")
    suspend fun getUsefulActivity() : UsefulActivityDto
}

object NetworkService {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val boredApiService: BoredApiService = retrofit.create(BoredApiService::class.java)
}