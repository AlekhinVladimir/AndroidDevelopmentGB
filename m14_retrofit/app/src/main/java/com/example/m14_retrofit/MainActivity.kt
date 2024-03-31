package com.example.m14_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import coil.load
import com.example.m14_retrofit.databinding.ActivityMainBinding
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fun loadFromApi() {
            RetrofitBackend.RetrofitInstance.searchImageApi.getImageList()
                .enqueue(object : Callback<RandomUser>{
                    override fun onResponse(
                        call: Call<RandomUser>,
                        response: Response<RandomUser>

                    ) {
                        Log.d("DEBUG", call.toString())
                        Log.d("DEBUG", response.toString())

                        if (response.isSuccessful) {
                            Log.d("DEBUG", "Person get started")

                            val person = response.body() ?: return
                            Log.d("DEBUG", "Person! : $person")

                            binding.photo.load(person.results[0].picture.large)
                            var fullName = person.results[0].name.title + " " + person.results[0].name.first + " " + person.results[0].name.last
                            binding.textView.text = fullName
                            binding.email.text = person.results[0].email
                            binding.gender.text = "Gender: ${person.results[0].gender}"
                            binding.date.text = "Age :" + person.results[0].dob.age
                        }
                        else{
                            Log.d("ERROR", response.toString())
                            Log.d("ERROR", call.toString())
                        }
                    }
                    override fun onFailure(call: Call<RandomUser>, t: Throwable) {
                        Log.d("DEBUG", t.toString())
                    }
                })
        }
        loadFromApi()

        binding.buttonRefresh.setOnClickListener {
            Log.d("DEBUG", "BUTTON PRESSED!")

            binding.textView.text = "Hello"
            loadFromApi()
        }
    }
}
