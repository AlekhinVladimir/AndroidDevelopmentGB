package com.example.m14_retrofit

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://randomuser.me/"

object RetrofitBackend {
    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val retrofitService: RetrofitService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        retrofit.create(RetrofitService::class.java)
    }

    fun getImageList(callback: (List<PersonModel>?, Throwable?) -> Unit) {
        retrofitService.getImageList().enqueue(object : retrofit2.Callback<RandomUser> {
            override fun onResponse(call: Call<RandomUser>, response: retrofit2.Response<RandomUser>) {
                if (response.isSuccessful) {
                    val randomUser = response.body()
                    val results = randomUser?.results
                    callback(results, null)
                } else {
                    val error = Throwable("Error: ${response.code()}")
                    callback(null, error)
                }
            }

            override fun onFailure(call: Call<RandomUser>, t: Throwable) {
                callback(null, t)
            }
        })
    }
}