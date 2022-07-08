package edu.neu.madcourse.stick_it_to_em;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ChatActivityViewHolder extends RecyclerView.ViewHolder {

    public TextView userDate;
    public TextView userMessage;
    public RelativeLayout msgSentLayoutId;
    public RelativeLayout msgRcdLayoutId;

    public ChatActivityViewHolder(View ChatActivityView) {
        super(ChatActivityView);
        this.userDate = ChatActivityView.findViewById(R.id.messageDate);
        this.userMessage =  ChatActivityView.findViewById(R.id.messageContent);
        this.msgSentLayoutId = ChatActivityView.findViewById(R.id.msgSentLayoutId);
        this.msgRcdLayoutId = ChatActivityView.findViewById(R.id.msgRcdLayoutId);
    }

    public void bindListData(ChatActivityData chatItem) {
        userDate.setText(chatItem.getUserDate());
        userMessage.setText(chatItem.getUserMsg());


    }
}
