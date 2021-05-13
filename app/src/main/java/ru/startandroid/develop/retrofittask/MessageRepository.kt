package ru.startandroid.develop.retrofittask

import ru.startandroid.develop.retrofittask.MessageDatabase.Companion.getMessageDatabase
import android.app.Application
import androidx.lifecycle.LiveData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.os.AsyncTask

class MessageRepository(
        private val application: Application) {
    private lateinit var jsonPlaceHolderApi: JsonPlaceHolderApi
    private val messageDao: MessageDao?
    val message: LiveData<List<MessageEntity?>?>?

    val messagesFromServer: Unit
        get() {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)
            val call = jsonPlaceHolderApi.messages

        }

    fun convertToEntity(responseFromServer: List<MessageApiModel>) {
        for (messageApiModel in responseFromServer) {
            val messageEntity = MessageEntity()
            messageEntity.id = messageApiModel.id
            messageEntity.userId = messageApiModel.userId
            messageEntity.title = messageApiModel.title
            messageEntity.text = messageApiModel.text
            insertMessage(messageEntity)
        }
    }

    fun insertMessage(messageEntity: MessageEntity?) {
        InsertMessageAsyncTask(messageDao).execute(messageEntity)
    }

    private open class InsertMessageAsyncTask(private val messageDao: MessageDao?) : AsyncTask<MessageEntity?, Void?, Void?>() {
        @JvmName("doInBackground1")
        protected fun doInBackground(vararg messageEntities: MessageEntity): Void? {
            messageDao!!.insert(messageEntities[0])
            return null
        }

        override fun doInBackground(vararg params: MessageEntity?): Void? {
            TODO("Not yet implemented")
        }
    }

    init {
        val messageDatabase = getMessageDatabase(application)
        messageDao = messageDatabase!!.messageDao
        messagesFromServer
        message = messageDao!!.allMessage
    }
}