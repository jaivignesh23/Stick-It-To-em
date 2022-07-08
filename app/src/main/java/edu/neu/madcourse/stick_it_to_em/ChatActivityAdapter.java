package edu.neu.madcourse.stick_it_to_em;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatActivityAdapter extends RecyclerView.Adapter<ChatActivityViewHolder>{

    private final List<ChatActivityData> chatList;
    private final Context context;

    public ChatActivityAdapter(List<ChatActivityData> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
    }


    @NonNull
    @Override
    public ChatActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatActivityViewHolder(LayoutInflater.from(context).inflate(R.layout.message_sent_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatActivityViewHolder holder, int position) {
        holder.bindListData(chatList.get(position));
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
}
