package edu.neu.madcourse.stick_it_to_em;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class FriendsListViewHolder extends RecyclerView.ViewHolder {

    public TextView friendName;
    public TextView friendEmail;
    public RelativeLayout friendsListLayout;

    public FriendsListViewHolder(View FriendsListView) {
        super(FriendsListView);
        this.friendName = FriendsListView.findViewById(R.id.friendListName);
        this.friendsListLayout = FriendsListView.findViewById(R.id.friendsListCardLayoutMain);
    }

    public void bindListData(FriendsListData friendItem) {
        friendName.setText(friendItem.getFriendFullName());
    }

}
