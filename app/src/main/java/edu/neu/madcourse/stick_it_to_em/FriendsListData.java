package edu.neu.madcourse.stick_it_to_em;

public class FriendsListData {

    private final String email;
    private final String full_name;
    private final String username;
    private final Integer sCount1;
    private final Integer sCount2;
    private final Integer sCount3;
    private final Integer sCount4;


    public FriendsListData(String email, String full_name, String username,
                           Integer sCount1, Integer sCount2,Integer sCount3, Integer sCount4) {

        this.email = email;
        this.full_name = full_name;
        this.username = username;
        this.sCount1 = sCount1;
        this.sCount2 = sCount2;
        this.sCount3 = sCount3;
        this.sCount4 = sCount4;
    }


    public String getFriendEmail() {
        return email;
    }

    public String getFriendFullName() {
        return full_name;
    }

    public String getFriendUserName() {
        return username;
    }

    public Integer getFriendStickerCount1() {
        return sCount1;
    }

    public Integer getFriendStickerCount2() {
        return sCount2;
    }

    public Integer getFriendStickerCount3() {
        return sCount3;
    }

    public Integer getFriendStickerCount4() {
        return sCount4;
    }

}
