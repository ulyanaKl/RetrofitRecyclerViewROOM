package ru.startandroid.develop.retrofittask
//test back to old commit
import retrofit2.Call
import retrofit2.http.GET

interface JsonPlaceHolderApi {
    @get:GET("posts")
    val messages: Call<List<MessageApiModel?>?>?
}