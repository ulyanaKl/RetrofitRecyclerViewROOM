package ru.startandroid.develop.retrofittask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.startandroid.develop.retrofittask.MyRecyclerViewAdapter.MyViewHolder

class MyRecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<MyViewHolder>() {
    private var messageList: List<MessageEntity>? = null
    private val callback: Callback? = null

    interface Callback {
        fun onItemLongClicked(message: MessageEntity?)
    }

    fun setItems(messageList: List<MessageEntity>?) {
        this.messageList = messageList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return MyViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val message = messageList!![position]
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return if (messageList == null) 0 else {
            messageList!!.size
        }
    }

    class MyViewHolder internal constructor(itemView: View, callback: Callback?) : ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.id)
        val userId: TextView = itemView.findViewById(R.id.user_Id)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val messageTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val callback: Callback? = callback
        fun bind(message: MessageEntity) {
            id.text = "Id: " + Integer.toString(message.id)
            userId.text = "UserId: " + Integer.toString(message.userId)
            titleTextView.text = "Title: " + message.title
            messageTextView.text = message.text
            itemView.setOnLongClickListener {
                callback!!.onItemLongClicked(message)
                false
            }
        }

    }
}