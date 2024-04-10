package com.example.m14_retrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.load
import com.example.m14_retrofit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = MainViewModel()

        viewModel.person.observe(this, Observer { person ->
            binding.apply {
                person?.let {
                    photo.load(it.picture.large)
                    val fullName = "${it.name.title} ${it.name.first} ${it.name.last}"
                    textView.text = fullName
                    email.text = it.email
                    gender.text = "Gender: ${it.gender}"
                    date.text = "Age: ${it.dob.age}"
                }
            }
        })

        viewModel.error.observe(this, Observer { error ->
            Log.e("ERROR", error?.message ?: "Unknown error occurred")
        })

        binding.buttonRefresh.setOnClickListener {
            viewModel.getImageList()
        }

        viewModel.getImageList()
    }
}