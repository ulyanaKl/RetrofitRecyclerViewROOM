package ru.startandroid.develop.retrofittask;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MessageEntity messageEntity);

    @Query("SELECT * FROM MessageEntity")
    LiveData<List<MessageEntity>> getAllMessage(); //LiveData потрібна коли ми вертаємо дані
}
