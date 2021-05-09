package ru.startandroid.develop.retrofittask

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MessageEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var userId = 0
    var title: String? = null
    var text: String? = null
}