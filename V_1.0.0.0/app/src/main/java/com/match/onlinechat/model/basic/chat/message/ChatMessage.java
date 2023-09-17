package com.match.onlinechat.model.basic.chat.message;

public class ChatMessage {

    private int userId;
    private String message = null;
    private String userName = null;

    public int getUserId() { return userId; }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
