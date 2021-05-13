package ru.startandroid.develop.retrofittask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class MessageViewModel(application: Application) : AndroidViewModel(application) {
    private val messageRepository: MessageRepository
    val message: LiveData<List<MessageEntity?>?>?
    fun insertMessage(messageEntity: MessageEntity?) {
        messageRepository.insertMessage(messageEntity)
    }

    init {
        messageRepository = MessageRepository(application)
        message = messageRepository.message
    }
}