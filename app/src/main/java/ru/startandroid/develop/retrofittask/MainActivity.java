package ru.startandroid.develop.retrofittask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private RecyclerView myRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private MessageDao messageDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button update = findViewById(R.id.updButton);
        update.setOnClickListener(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        myRecyclerView = findViewById(R.id.myRecyclerView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this);
        myRecyclerView.setAdapter(adapter);

        initializeDatabaseAndDao();
        presentDataFromDB();

    }

    private void getMessages() {

        Call<List<MessageApiModel>> call = jsonPlaceHolderApi.getMessages();

        call.enqueue(new Callback<List<MessageApiModel>>() {
            @Override
            public void onResponse(Call<List<MessageApiModel>> call, Response<List<MessageApiModel>> response) {
                if (!response.isSuccessful()) {

                    return; //виходить з методу, тут зупиняє виконання методу онРеспонс
                    //брейком можна вийти з циклу або свіча
                }

                List<MessageApiModel> responseFromServer = response.body();
//додатковий метод який конвертує лісти
                messageDao.save(convertToEntity(responseFromServer));
                presentDataFromDB();
            }
            @Override
            public void onFailure(Call<List<MessageApiModel>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        getMessages();
    }

    public List<MessageEntity> convertToEntity(List<MessageApiModel> responseFromServer) {

        List<MessageEntity> newList = new ArrayList<>();

        for (MessageApiModel messageApiModel : responseFromServer) {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setId(messageApiModel.getId());
            messageEntity.setUserId(messageApiModel.getUserId());
            messageEntity.setTitle(messageApiModel.getTitle());
            messageEntity.setText(messageApiModel.getText());

            newList.add(messageEntity);

        }
        return newList;
    }

    private void initializeDatabaseAndDao() {
        MessageDatabase myDatabase = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        messageDao = myDatabase.getMessageDao();
    }

    private void presentDataFromDB() {
        List<MessageEntity> messageEntityList = messageDao.getAllMessage();
        adapter.setItems(messageEntityList);
        adapter.notifyDataSetChanged();
    }

}