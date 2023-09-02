package com.match.onlinechat.model.basic.hall;

import com.match.onlinechat.model.basic.client.Client;
import com.match.onlinechat.model.basic.tools.ParsingTools;
import com.match.onlinechat.model.basic.tools.GeneratingTools;
import com.match.onlinechat.model.basic.user.User;

import java.util.ArrayList;

import static com.alibaba.fastjson.JSON.toJSONString;

public class Hall {
    private Client client;
    private GeneratingTools generatingTools = new GeneratingTools();
    public ParsingTools parsingTools;
    public static String searchResult;
    public User user;

    public Hall() { }
    public Hall(Client client){
        this.client = client;
    }

    public void selectRoom(String name){
        try {
            generatingTools.json("mode", "selectRoom");
            generatingTools.json("id", user.getId());
            generatingTools.json("userName", user.getName());
            generatingTools.json("chatRoom", name);
            client.send(toJSONString(generatingTools.getJson()));
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void newRoom(String name){
        try{
            generatingTools.json("mode", "newRoom");
            generatingTools.json("NewRoomName", name);
            client.send(toJSONString(generatingTools.getJson()));
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setSearchResult(String searchResult){
        Hall.searchResult = searchResult;
    }

    public String getSearchResult(){
        return Hall.searchResult;
    }

    public void searchRoom(String name){
        try{
            generatingTools.json("mode", "search");
            generatingTools.json("chatRoomName", name);
            client.send(toJSONString(generatingTools.getJson()));
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setChatRoom(String msg){
        this.parsingTools = new ParsingTools(msg);
    }

    public ArrayList<String> getChatRoomList(){
        int roomNumbers = (int) parsingTools.get("roomNumbers");
        ArrayList<String> chatRoomList = new ArrayList<>();
        for(int i = 1; i <= roomNumbers; i++){
            chatRoomList.add(parsingTools.getString("room"+i));
        }
        return chatRoomList;
    }
    public void roomList(){
        try {
            generatingTools.initJson();
            generatingTools.json("mode", "getRoomList");
            client.send(toJSONString(generatingTools.getJson()));
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
