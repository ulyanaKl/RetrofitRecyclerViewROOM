package ru.startandroid.develop.retrofittask;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageRepository {
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MessageDao messageDao;
    private LiveData<List<MessageEntity>> messageEntity;
    private Application application;

    public MessageRepository(Application application) {
        this.application = application;
        MessageDatabase messageDatabase = MessageDatabase.getMessageDatabase(application);
        messageDao = messageDatabase.getMessageDao();
        getMessagesFromServer();
        messageEntity = messageDao.getAllMessage();
    }

    //getMessages from server
    public void getMessagesFromServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<MessageApiModel>> call = jsonPlaceHolderApi.getMessages();

        call.enqueue(new Callback<List<MessageApiModel>>() {
            @Override
            public void onResponse(Call<List<MessageApiModel>> call, Response<List<MessageApiModel>> response) {
                if (!response.isSuccessful()) {

                    return; //виходить з методу, тут зупиняє виконання методу онРеспонс
                    //брейком можна вийти з циклу або свіча
                }
                //якщо респонс успішний
                List<MessageApiModel> responseFromServer = response.body();
                //додатковий метод який конвертує лісти
                convertToEntity(responseFromServer);
                // presentDataFromDB();
            }

            @Override
            public void onFailure(Call<List<MessageApiModel>> call, Throwable t) {

            }
        });
    }

    public void convertToEntity(List<MessageApiModel> responseFromServer) {

        for (MessageApiModel messageApiModel : responseFromServer) {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setId(messageApiModel.getId());
            messageEntity.setUserId(messageApiModel.getUserId());
            messageEntity.setTitle(messageApiModel.getTitle());
            messageEntity.setText(messageApiModel.getText());

            insertMessage(messageEntity);
        }
    }

    public LiveData<List<MessageEntity>> getMessage(){
        return messageEntity;
    }

    public void insertMessage(MessageEntity messageEntity) {
        new InsertMessageAsyncTask(messageDao).execute(messageEntity);
    }

    private static class InsertMessageAsyncTask extends AsyncTask<MessageEntity, Void, Void> {
        private MessageDao messageDao;

        public InsertMessageAsyncTask(MessageDao messageDao) {
            this.messageDao = messageDao;
        }

        @Override
        protected Void doInBackground(MessageEntity... messageEntities) {
            messageDao.insert(messageEntities[0]);
            return null;
        }
    }
}
