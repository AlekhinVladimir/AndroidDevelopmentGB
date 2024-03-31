package com.example.m14_retrofit
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass



@JsonClass(generateAdapter = true)
data class PersonModel(
    @Json(name = "gender") val gender: String,
    @Json(name = "name") val name: Name,
    @Json(name = "email") val email: String,
    @Json(name = "dob") val dob: DoB,
    @Json(name = "picture") val picture: Picture
)

@JsonClass(generateAdapter = true)
data class RandomUser(
    @Json(name = "results") val results: List<PersonModel>
)

@JsonClass(generateAdapter = true)
data class Name(
    @Json(name = "title") val title: String,
    @Json(name = "first") val first: String,
    @Json(name = "last") val last: String
)

@JsonClass(generateAdapter = true)
data class DoB(
    @Json(name = "date") val date: String,
    @Json(name = "age") val age: Int
)
@JsonClass(generateAdapter = true)
data class Picture(
    @Json(name = "large") val large: String,
    @Json(name = "medium") val medium: String,
    @Json(name = "thumbnail") val thumbnail: String
)
