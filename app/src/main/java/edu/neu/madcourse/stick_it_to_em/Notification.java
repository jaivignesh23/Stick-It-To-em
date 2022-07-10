package edu.neu.madcourse.stick_it_to_em;

public class Notification {

    public final int conversationID, stickerID;
    public final String senderID, receiverID;
    public final long timestamp;

    public Notification(int conversationID, String senderID, String receiverID, int stickerID) {
        this.conversationID = conversationID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.stickerID = stickerID;
        this.timestamp = System.currentTimeMillis();
    }
}
