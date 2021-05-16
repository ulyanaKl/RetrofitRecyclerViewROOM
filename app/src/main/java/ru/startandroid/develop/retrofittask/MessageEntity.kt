package ru.startandroid.develop.retrofittask

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageEntity(@PrimaryKey(autoGenerate = true) var id: Int = 0,
                         var userId: Int = 0,
                         var title: String? = null,
                         var text: String? = null)