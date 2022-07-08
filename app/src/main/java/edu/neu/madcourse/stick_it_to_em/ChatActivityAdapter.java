package edu.neu.madcourse.stick_it_to_em;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatActivityAdapter extends RecyclerView.Adapter<ChatActivityViewHolder>{

    private final List<ChatActivityData> chatList;
    private final Context context;
    private final String username;

    public ChatActivityAdapter(List<ChatActivityData> chatList, Context context, String username) {
        this.chatList = chatList;
        this.context = context;
        this.username = username;
    }


    @NonNull
    @Override
    public ChatActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatActivityViewHolder(LayoutInflater.from(context).inflate(R.layout.message_sent_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatActivityViewHolder holder, int position) {

//        ChatActivityData chatItem = chatList.get(position);
//        if(chatItem.getUserDate().equals("07082022")) {
//            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//            TextView textView = this.context.fifindViewById(R.id.messageContent);
//            textView.setLayoutParams(layoutParams);
//            holder.msgSentLayoutId.setVisibility(View.GONE);
//        }
//        else{
//            holder.msgSentLayoutId.setVisibility(View.VISIBLE);
//        }

        holder.bindListData(chatList.get(position), username);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
}
