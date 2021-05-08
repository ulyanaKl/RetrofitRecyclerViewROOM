package ru.startandroid.develop.retrofittask

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(messageEntity: MessageEntity?)

    //LiveData потрібна коли ми вертаємо дані
    @get:Query("SELECT * FROM MessageEntity")
    val allMessage: LiveData<List<MessageEntity?>?>?
}