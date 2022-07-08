package edu.neu.madcourse.stick_it_to_em;

public class ChatActivityData {

    private final String message;
    private final String date;

    public ChatActivityData(String message, String date) {
        this.message = message;
        this.date = date;
    }

    public String getUserDate() {
        return date;
    }

    public String getUserMsg() {
        return message;
    }
}
