package ru.startandroid.develop.retrofittask;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MessageEntity.class}, version = 3, exportSchema = false)
public abstract class MessageDatabase extends RoomDatabase {
    private static MessageDatabase messageDatabase;
    public abstract MessageDao getMessageDao();

    public static MessageDatabase getMessageDatabase(Context context) {
        if(messageDatabase == null) {
            messageDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    MessageDatabase.class, "database-name")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return messageDatabase;
    }

}