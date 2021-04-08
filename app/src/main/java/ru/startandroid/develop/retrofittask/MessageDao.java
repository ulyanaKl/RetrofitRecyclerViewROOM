package ru.startandroid.develop.retrofittask;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(List<MessageEntity> messageEntity);

    @Query("SELECT * FROM MessageEntity")
    List<MessageEntity> getAllMessage();
}
