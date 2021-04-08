package ru.startandroid.develop.retrofittask;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MessageEntity.class}, version = 3, exportSchema = false)
public abstract class MessageDatabase extends RoomDatabase {
    public abstract MessageDao getMessageDao();


}