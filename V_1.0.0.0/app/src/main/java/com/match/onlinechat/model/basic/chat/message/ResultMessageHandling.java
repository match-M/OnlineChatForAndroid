package com.match.onlinechat.model.basic.chat.message;

/**
 * @author match
 */
public class ResultMessageHandling {

    public static String chatUserName;
    public static int chatUserId;

    public static String chatMessage;

    public String getName(){
        return chatUserName;
    }
    public int getId(){
        return chatUserId;
    }
    public String getMessage() {
        return chatMessage;
    }



}
