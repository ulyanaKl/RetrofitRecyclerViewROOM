package ru.startandroid.develop.retrofittask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private List<MessageEntity> messageList;
    private Context context;
    private Callback callback;

    interface Callback{
        void onItemLongClicked(MessageEntity message);
    }

    public MyRecyclerViewAdapter(Context context){
        this.context = context;
    }

    public void setItems(List<MessageEntity> messageList){
        this.messageList = messageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view, callback);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MessageEntity message = messageList.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        if(messageList == null)
            return  0;
        else {
            return messageList.size();
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView id;
        private TextView userId;
        private TextView titleTextView;
        private TextView messageTextView;
        private Callback callback;

        public MyViewHolder(@NonNull View itemView, Callback callback) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            userId = itemView.findViewById(R.id.user_Id);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            this.callback = callback;
        }

        public void bind(final MessageEntity message){
            id.setText("Id: " + Integer.toString(message.getId()));
            userId.setText("UserId: " + Integer.toString(message.getUserId()));
            titleTextView.setText("Title: " + message.getTitle());
            messageTextView.setText(message.getText());

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    callback.onItemLongClicked(message);
                    return false;
                }
            });

        }
    }
}
