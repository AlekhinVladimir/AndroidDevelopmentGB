package com.example.m14_retrofit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val person: MutableLiveData<PersonModel?> = MutableLiveData()
    val error: MutableLiveData<Throwable?> = MutableLiveData()

    fun getImageList() {
        RetrofitBackend.getImageList { results, throwable ->
            if (throwable != null) {
                error.postValue(throwable)
            } else {
                val firstPerson = results?.firstOrNull()
                person.postValue(firstPerson)
            }
        }
    }
}