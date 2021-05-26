package ru.startandroid.develop.retrofittask

import ru.startandroid.develop.retrofittask.MessageDatabase.Companion.getMessageDatabase
import android.app.Application
import androidx.lifecycle.LiveData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageRepository (
        private val application: Application) {
    private lateinit var jsonPlaceHolderApi: JsonPlaceHolderApi
    private val messageDao: MessageDao?
    val message: LiveData<List<MessageEntity>>

    suspend fun fetchData(): List<MessageApiModel>{
        var messageApiModelList: List<MessageApiModel> = emptyList<MessageApiModel>()
        val retrofit = Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)

        val call = jsonPlaceHolderApi.messages
        call.enqueue(object : Callback<List<MessageApiModel>> {
            override fun onResponse(call: Call<List<MessageApiModel>>, response: Response<List<MessageApiModel>>) {
                val messages: List<MessageApiModel> = response.body()!!
                messageApiModelList = messages
            }

            override fun onFailure(call: Call<List<MessageApiModel>>, t: Throwable) {}
        })
        return messageApiModelList
    }

    suspend fun fetchAndInsertData(){
        val fetchApiModels = fetchData()
        val convertedList:List<MessageEntity> = convertToEntity(fetchApiModels)
        messageDao!!.insert(convertedList)
    }

    fun convertToEntity(responseFromServer: List<MessageApiModel>) : List<MessageEntity> {
        val list: MutableList<MessageEntity> = ArrayList()
        for (messageApiModel in responseFromServer) {
            val messageEntity = MessageEntity(
            id = messageApiModel.id,
            userId = messageApiModel.userId,
            title = messageApiModel.title,
            text = messageApiModel.text)
            list.add(messageEntity)
        }
        return list
    }

    /*fun insertMessage(messageEntities: List<MessageEntity>) {
        val messageEntityArray = messageEntities.toTypedArray()
        InsertMessageAsyncTask(messageDao).execute(*messageEntityArray)
    }

    private open class InsertMessageAsyncTask(private val messageDao: MessageDao?) : AsyncTask<MessageEntity, Void?, Void?>() {

        override fun doInBackground(vararg messageEntities: MessageEntity): Void? {
            messageDao!!.insert(listOf(*messageEntities))
            return null
        }
    }*/



    init {
        val messageDatabase = getMessageDatabase(application)
        messageDao = messageDatabase.messageDao
        message = messageDao!!.allMessage()
    }
}