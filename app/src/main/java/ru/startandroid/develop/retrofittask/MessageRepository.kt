package ru.startandroid.develop.retrofittask

import ru.startandroid.develop.retrofittask.MessageDatabase.Companion.getMessageDatabase
import android.app.Application
import androidx.lifecycle.LiveData
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.*

class MessageRepository(application: Application) {
    private lateinit var jsonPlaceHolderApi: JsonPlaceHolderApi
    private val messageDao: MessageDao?
    val message: LiveData<List<MessageEntity>>

    private suspend fun fetchData() : List<MessageApiModel> {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)

        val call = jsonPlaceHolderApi.messages.awaitResponse()
        val messages = call.body()!!

        return messages
    }
    suspend fun fetchAndInsertData(){
        val fetchApiModels = fetchData()
        val convertedList:List<MessageEntity> = convertToEntity(fetchApiModels)
        messageDao!!.insert(convertedList)
    }

    private fun convertToEntity(responseFromServer: List<MessageApiModel>) : List<MessageEntity> {
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

    init {
        val messageDatabase = getMessageDatabase(application)
        messageDao = messageDatabase.messageDao
        message = messageDao!!.allMessage()
    }
}