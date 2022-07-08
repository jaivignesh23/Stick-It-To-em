package edu.neu.madcourse.stick_it_to_em;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
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

        // TODO : If the username is same as the sender name
        if(chatItem.getUserDate().equals("07082022"))
        {
            RelativeLayout.LayoutParams paramsMsg = (RelativeLayout.LayoutParams)userMessage.getLayoutParams();
            paramsMsg.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            paramsMsg.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);

            RelativeLayout.LayoutParams paramsDate = (RelativeLayout.LayoutParams)userDate.getLayoutParams();
            paramsDate.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            paramsDate.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);

            userMessage.setLayoutParams(paramsMsg);
            userMessage.setBackgroundResource(R.drawable.rounded_corner_sent);

            userDate.setLayoutParams(paramsDate);
        }
        else{
            RelativeLayout.LayoutParams paramsMsg = (RelativeLayout.LayoutParams)userMessage.getLayoutParams();
            paramsMsg.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            paramsMsg.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);

            RelativeLayout.LayoutParams paramsDate = (RelativeLayout.LayoutParams)userDate.getLayoutParams();
            paramsDate.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            paramsDate.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);

            userMessage.setLayoutParams(paramsMsg);
            userMessage.setBackgroundResource(R.drawable.rounded_corner_received);
            userDate.setLayoutParams(paramsDate);
        }


        userDate.setText(chatItem.getUserDate());
        userMessage.setText(chatItem.getUserMsg());



    }
}
