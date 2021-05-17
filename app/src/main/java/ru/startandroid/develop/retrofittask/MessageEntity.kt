package ru.startandroid.develop.retrofittask

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageEntity(@PrimaryKey(autoGenerate = true) var id: Int,
                         var userId: Int,
                         var title: String?,
                         var text: String?)