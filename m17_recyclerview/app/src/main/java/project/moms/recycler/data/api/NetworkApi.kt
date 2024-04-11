package project.moms.recycler.data.api

import project.moms.recycler.models.MarsPhotosResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.nasa.gov"
private const val API_KEY_NASA = "hEvq31kFLqrYeTroZVF82vGtPOpFVToZxCsC4NPo"

object RetrofitObj {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val networkApi:NetworkApi = retrofit.create(NetworkApi::class.java)
}

interface NetworkApi {
//    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
//    @GET("/mars-photos/api/v1/rovers/curiosity/photos?earth_date=2022-1-1&api_key=$API_KEY_NASA")
//    suspend fun getMarsPhotos(): MarsPhotosResponse

    @GET("/mars-photos/api/v1/rovers/curiosity/photos?max_date=")
    suspend fun getMarsPhotos(
        @Query("earth_date") earthDate: String,
        @Query("api_key") apiKey: String = API_KEY_NASA
    ): MarsPhotosResponse
}