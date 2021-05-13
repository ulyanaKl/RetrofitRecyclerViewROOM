package ru.startandroid.develop.retrofittask

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// kotlin
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var myRecyclerView: RecyclerView
    private var adapter: MyRecyclerViewAdapter? = null
    var model: MessageViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val update = findViewById<Button>(R.id.updButton)
        update.setOnClickListener(this)
        myRecyclerView = findViewById(R.id.myRecyclerView)
        myRecyclerView.setLayoutManager(LinearLayoutManager(this))
        adapter = MyRecyclerViewAdapter(this)
        myRecyclerView.setAdapter(adapter)
        model = AndroidViewModelFactory(this.application).create(MessageViewModel::class.java)
    }

    override fun onClick(v: View) {
        messageLiveData
    }

    private val messageLiveData: Unit
        private get() {
            model!!.message!!.observe(this, { messageEntities -> adapter!!.setItems(messageEntities as List<MessageEntity>?) })
        }
}