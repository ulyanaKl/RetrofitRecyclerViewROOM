package ru.startandroid.develop.retrofittask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MessageViewModel(application: Application) : AndroidViewModel(application) {
    private val messageRepository: MessageRepository = MessageRepository(application)
    val message: LiveData<List<MessageEntity>> = messageRepository.message

    fun insertMessage() {
        viewModelScope.launch(IO) {
                messageRepository.fetchAndInsertData()
        }
    }

}