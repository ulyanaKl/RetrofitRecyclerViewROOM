package ru.startandroid.develop.retrofittask

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(messageEntity: List<MessageEntity>)

    //LiveData потрібна коли ми вертаємо дані
    @Query("SELECT * FROM MessageEntity")
    fun allMessage(): LiveData<List<MessageEntity?>?>?
}