package edu.neu.madcourse.stick_it_to_em;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListViewHolder> {

    private final List<FriendsListData> friendsList;
    private final Context context;
    private FriendsListSelectItem friendsListSelectItemListener;

    public FriendsListAdapter(List<FriendsListData> friendsList, Context context, FriendsListSelectItem friendsListSelectItemListener) {

        this.friendsList = friendsList;
        this.context = context;
        this.friendsListSelectItemListener = friendsListSelectItemListener;
    }

    @NonNull
    @Override
    public FriendsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendsListViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_friends_list_card_view,null));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsListViewHolder holder, int position) {
        holder.bindListData(friendsList.get(position));

        holder.friendsListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendsListSelectItemListener.onSelectFriendToSendMessage(friendsList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }
}
