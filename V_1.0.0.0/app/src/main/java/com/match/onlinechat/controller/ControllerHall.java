package com.match.onlinechat.controller;


import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.match.onlinechat.activity.NewChatRoomActivity;
import com.match.onlinechat.activity.SearchChatRoomActivity;
import com.match.onlinechat.activity.SignupActivity;
import com.match.onlinechat.model.basic.client.Client;
import com.match.onlinechat.model.basic.constants.NewChatRoomPrompt;
import com.match.onlinechat.model.basic.constants.SearchChatRoomPrompt;
import com.match.onlinechat.model.basic.constants.SignupPrompt;
import com.match.onlinechat.model.basic.hall.Hall;

import com.match.onlinechat.activity.HallActivity;
import com.match.onlinechat.model.basic.hall.Register;
import com.match.onlinechat.model.basic.user.User;

import java.util.ArrayList;

public class ControllerHall {


    private User user;
    private Client client;
    public static Hall hall;
    private Register register;
    public static ArrayList<String> list;
    public static SignupActivity signupActivity;
    public static NewChatRoomActivity newChatRoomActivity;
    public static SearchChatRoomActivity searchChatRoomActivity;

    public ControllerHall(){ }

    public void init() {
        list = new ArrayList<>();
        client = HallActivity.client;
        user = new User();
        hall = new Hall(client);
        System.out.println(client);
    }

    public void getChatRoomList(){
        hall.roomList();
    }

    public void showChatRoomList(){
        //在ui线程中更新listview
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                list = hall.getChatRoomList(); //获取聊天室列表
                HallActivity.chatRoomList.setAdapter(HallActivity.adapter); //设置适配器
            }
        });
    }

    public void signup(SignupActivity signupActivity, String name){
        if(name.trim().length() == 0) return;
        if(user.getName()!=null){
            Toast.makeText(signupActivity, SignupPrompt.MULTIPLE_REGISTRATION,
                    Toast.LENGTH_LONG).show();
            return;
        }
        if(name.length() > 18) {
            Toast.makeText(signupActivity, SignupPrompt.LENGTH_EXCEEDS_LIMIT,
                    Toast.LENGTH_LONG).show();
            return;
        }
        ControllerHall.signupActivity = signupActivity;
        user.setName(name);
        register = new Register(client, user);
        register.singUp();
    }

    public void showId(){
        //在ui线程中显示toast
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ControllerHall.signupActivity, SignupPrompt.PROMPT_ID+user.getId(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void newChatRoom(NewChatRoomActivity newChatRoomActivity, String name){
        if(name.trim().length() == 0) return;
        if(name.length() > 20) {
            Toast.makeText(signupActivity, NewChatRoomPrompt.LENGTH_EXCEEDS_LIMIT,
                    Toast.LENGTH_LONG).show();
            return;
        }
        ControllerHall.newChatRoomActivity = newChatRoomActivity;
        hall.newRoom(name);
    }

    public void showNewChatRoomResult(String result){
        //在ui线程中显示toast
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ControllerHall.newChatRoomActivity, result,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void searchChatRoom(SearchChatRoomActivity searchChatRoomActivity, String name){
        ControllerHall.searchChatRoomActivity = searchChatRoomActivity;
        hall.searchRoom(name);
    }

    public void setSearchResult(){
        String[] resultList = new String[]{hall.getSearchResult()};
        //在ui线程中更新搜索结果列表
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                SearchChatRoomActivity.notFoundChatRoom.setText("");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(searchChatRoomActivity,
                        android.R.layout.simple_list_item_1,
                        resultList);
                SearchChatRoomActivity.searchResult.setAdapter(adapter);
            }
        });
    }

    public void setNotFoundResult(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(searchChatRoomActivity,
                        android.R.layout.simple_list_item_1);
                SearchChatRoomActivity.searchResult.setAdapter(adapter);
                SearchChatRoomActivity.notFoundChatRoom.setText(hall.getSearchResult());
            }
        });
    }

}
