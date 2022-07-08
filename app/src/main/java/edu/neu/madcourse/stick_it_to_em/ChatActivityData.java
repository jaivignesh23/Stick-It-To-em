package edu.neu.madcourse.stick_it_to_em;

public class ChatActivityData {

    private final String message;
    private final String date;
    private final String senderUserName;
    private final String receiverUsername;
    private final Integer stickerId;
    private final Integer conversationId;

    public ChatActivityData(String message, String date, String senderUserName
            ,String receiverUsername, Integer stickerId, Integer conversationId) {
        this.message = message;
        this.date = date;
        this.senderUserName = senderUserName;
        this.receiverUsername = receiverUsername;
        this.stickerId = stickerId;
        this.conversationId = conversationId;
    }

    public String getUserDate() {
        return date;
    }

    public String getUserMsg() {
        return message;
    }

    public String getSender() {
        return senderUserName;
    }

    public String getReceiver() {
        return receiverUsername;
    }

    public Integer getStickerId() {
        return stickerId;
    }

    public Integer getConversationId() {
        return conversationId;
    }

}
