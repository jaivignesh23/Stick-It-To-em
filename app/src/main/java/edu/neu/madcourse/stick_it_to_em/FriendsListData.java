package edu.neu.madcourse.stick_it_to_em;

public class FriendsListData {

    private final Integer friendId;
    private final String friendName;
    private final String friendEmailId;

    public FriendsListData(Integer friendId, String friendName, String friendEmailId) {
        this.friendId = friendId;
        this.friendName = friendName;
        this.friendEmailId = friendEmailId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public String getFriendEmailId() {
        return friendEmailId;
    }
}
