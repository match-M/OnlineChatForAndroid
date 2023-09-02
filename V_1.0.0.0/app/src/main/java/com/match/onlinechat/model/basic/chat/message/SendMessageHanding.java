package com.match.onlinechat.model.basic.chat.message;

import com.match.onlinechat.model.basic.client.Client;
import com.match.onlinechat.model.basic.tools.GeneratingTools;
import com.match.onlinechat.model.basic.user.User;

import static com.alibaba.fastjson.JSON.toJSONString;

public class SendMessageHanding {
    private User user;
    private Client client;
    private String sendMessage;
    private String roomName;
    private GeneratingTools generatingTools = new GeneratingTools();

    public SendMessageHanding(Client client, User user) {
        this.user = user;
        this.client = client;
    }

    public void setRoomName(String roomName){
        this.roomName = roomName;
    }

    public void message(String sendMessage){
        this.sendMessage = sendMessage;
        this.sendMessage(handing());
    }

    public String handing(){
        generatingTools.json("message", this.sendMessage);
        return toJSONString(generatingTools.getJson());
    }

    public void sendMessage(String message){
        try {
            client.send(message);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
