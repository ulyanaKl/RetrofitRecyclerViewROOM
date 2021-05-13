package ru.startandroid.develop.retrofittask

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewKotlinClass {
    private fun getMessages() {
        val call: Call<List<MessageApiModel>>?? = null
        call!!.enqueue(object: Callback<List<MessageApiModel>>{
            override fun onResponse(call: Call<List<MessageApiModel>>, response: Response<List<MessageApiModel>>) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<List<MessageApiModel>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}