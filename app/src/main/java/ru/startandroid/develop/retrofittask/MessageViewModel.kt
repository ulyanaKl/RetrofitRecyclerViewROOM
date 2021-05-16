package ru.startandroid.develop.retrofittask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class MessageViewModel(application: Application) : AndroidViewModel(application) {
    private val messageRepository: MessageRepository = MessageRepository(application)
    val message: LiveData<List<MessageEntity?>?>? = messageRepository.message
    fun insertMessage() {
        messageRepository.fetchData()
    }

}