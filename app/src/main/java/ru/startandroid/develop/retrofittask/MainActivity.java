package ru.startandroid.develop.retrofittask;
// kotlin
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView myRecyclerView;
    private MyRecyclerViewAdapter adapter;
    public MessageViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button update = findViewById(R.id.updButton);
        update.setOnClickListener(this);

        myRecyclerView = findViewById(R.id.myRecyclerView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this);
        myRecyclerView.setAdapter(adapter);

        model = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(MessageViewModel.class);
    }

    @Override
    public void onClick(View v) {
        getMessageLiveData();
    }

    private void getMessageLiveData(){
        model.getMessage().observe(this, new Observer<List<MessageEntity>>() {
            @Override
            public void onChanged(List<MessageEntity> messageEntities) {
                adapter.setItems(messageEntities);
            }
        });
    }
}