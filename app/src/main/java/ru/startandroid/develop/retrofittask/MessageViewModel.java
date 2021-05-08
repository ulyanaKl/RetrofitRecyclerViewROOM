package ru.startandroid.develop.retrofittask;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MessageViewModel extends AndroidViewModel {
    private MessageRepository messageRepository;
    private LiveData<List<MessageEntity>> messages;

    public MessageViewModel(@NonNull Application application) {
        super(application);
        this.messageRepository = new MessageRepository(application);
        messages = messageRepository.getMessage();
    }

    public void insertMessage(MessageEntity messageEntity){
        messageRepository.insertMessage(messageEntity);
    }

    public LiveData<List<MessageEntity>> getMessage(){
        return messages;
    }

}
