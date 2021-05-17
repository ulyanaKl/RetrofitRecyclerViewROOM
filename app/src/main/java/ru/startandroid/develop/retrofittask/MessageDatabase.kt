package ru.startandroid.develop.retrofittask

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MessageEntity::class], version = 3, exportSchema = false)
abstract class MessageDatabase : RoomDatabase() {
    abstract val messageDao: MessageDao

    companion object {
        private var messageDatabase: MessageDatabase? = null
        fun getMessageDatabase(context: Context): MessageDatabase {
            if (messageDatabase == null) {
                messageDatabase = Room.databaseBuilder(context.applicationContext,
                        MessageDatabase::class.java, "database-name")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
            }
            return messageDatabase!!
        }
    }
}