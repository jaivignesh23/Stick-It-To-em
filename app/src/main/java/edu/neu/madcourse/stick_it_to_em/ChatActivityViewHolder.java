package edu.neu.madcourse.stick_it_to_em;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ChatActivityViewHolder extends RecyclerView.ViewHolder {

    public TextView userDate;
    public TextView userMessage;
    public RelativeLayout msgSentLayoutId;
    public RelativeLayout msgRcdLayoutId;
    public ImageView stickerImg;

    //public ImageView stickerImg;

    public ChatActivityViewHolder(View ChatActivityView) {
        super(ChatActivityView);
//        this.userDate = ChatActivityView.findViewById(R.id.messageDate);
//        this.userMessage =  ChatActivityView.findViewById(R.id.messageContent);
        this.msgSentLayoutId = ChatActivityView.findViewById(R.id.msgSentLayoutId);
        this.msgRcdLayoutId = ChatActivityView.findViewById(R.id.msgRcdLayoutId);
        this.stickerImg = ChatActivityView.findViewById(R.id.STICKER_IMAGE);
    }

    public void bindListData(ChatActivityData chatItem, String username) {

        // TODO : If the username is same as the sender name
        RelativeLayout.LayoutParams paramsMsg = (RelativeLayout.LayoutParams)stickerImg.getLayoutParams();
        if(chatItem.getSender().equals(username))
        {

            paramsMsg.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            paramsMsg.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);

//            RelativeLayout.LayoutParams paramsDate = (RelativeLayout.LayoutParams)userDate.getLayoutParams();
//            paramsDate.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//            paramsDate.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);

            stickerImg.setLayoutParams(paramsMsg);
//            userMessage.setBackgroundResource(R.drawable.rounded_corner_sent);

//            userDate.setLayoutParams(paramsDate);
        }
        else{
            paramsMsg.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            paramsMsg.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);

//            RelativeLayout.LayoutParams paramsDate = (RelativeLayout.LayoutParams)userDate.getLayoutParams();
//            paramsDate.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//            paramsDate.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);

            stickerImg.setLayoutParams(paramsMsg);
//            userMessage.setBackgroundResource(R.drawable.rounded_corner_received);
//            userDate.setLayoutParams(paramsDate);
        }

//        if (chatItem.getSender().equals(username)) {
//            RelativeLayout.LayoutParams paramsMsg = (RelativeLayout.LayoutParams)userMessage.getLayoutParams();
//            paramsMsg.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//            paramsMsg.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
//
//            RelativeLayout.LayoutParams paramsDate = (RelativeLayout.LayoutParams)userDate.getLayoutParams();
//            paramsDate.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//            paramsDate.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
//
//            userMessage.setLayoutParams(paramsMsg);
////            userMessage.setBackgroundResource(R.drawable.rounded_corner_sent);
//
//            userDate.setLayoutParams(paramsDate);
//        }
//        else if (chatItem.getReceiver().equals()){
//            RelativeLayout.LayoutParams paramsMsg = (RelativeLayout.LayoutParams)userMessage.getLayoutParams();
//            paramsMsg.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//            paramsMsg.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
//
//            RelativeLayout.LayoutParams paramsDate = (RelativeLayout.LayoutParams)userDate.getLayoutParams();
//            paramsDate.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//            paramsDate.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
//
//            userMessage.setLayoutParams(paramsMsg);
////            userMessage.setBackgroundResource(R.drawable.rounded_corner_received);
//            userDate.setLayoutParams(paramsDate);
//        }

        // TODO: Get the sticker id from chatItem and set the image from resource.

        //userDate.setText(chatItem.getUserDate());
        //userMessage.setText("");

        // TODO:
        Integer stickerID = chatItem.getStickerId();
        if (stickerID == 1) {
            this.stickerImg.setImageResource(R.mipmap.sticker1_round);
            //userMessage.setText("This is the first image");
        } else if (stickerID == 2) {
            this.stickerImg.setImageResource(R.mipmap.sticker2_round);
            //userMessage.setText("This is the second image");
        } else if (stickerID == 3) {
            this.stickerImg.setImageResource(R.mipmap.sticker3_round);
            //userMessage.setText("This is the third image");
        } else {
            this.stickerImg.setImageResource(R.mipmap.sticker4_round);
            //userMessage.setText("This is the fourth image");
        }
    }
}
