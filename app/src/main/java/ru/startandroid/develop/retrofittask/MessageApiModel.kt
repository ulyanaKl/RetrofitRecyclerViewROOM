package ru.startandroid.develop.retrofittask

import com.google.gson.annotations.SerializedName

class MessageApiModel {
    val userId = 0
    val id = 0
    var title: String? = null

    @SerializedName("body")
    var text: String? = null
}