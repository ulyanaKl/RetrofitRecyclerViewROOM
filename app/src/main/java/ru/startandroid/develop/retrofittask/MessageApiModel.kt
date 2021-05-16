package ru.startandroid.develop.retrofittask

import com.google.gson.annotations.SerializedName

data class MessageApiModel(val userId: Int, val id: Int, val title: String, @SerializedName("body") val text: String)