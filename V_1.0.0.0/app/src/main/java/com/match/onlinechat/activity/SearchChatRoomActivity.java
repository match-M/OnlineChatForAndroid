package com.match.onlinechat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.match.onlinechat.R;
import com.match.onlinechat.controller.ControllerHall;
import com.match.onlinechat.model.basic.constants.SearchChatRoomPrompt;

public class SearchChatRoomActivity extends AppCompatActivity {

    public static String searchText;
    public static ListView searchResult;
    public ControllerHall controllerHall;
    public static TextView notFoundChatRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_chat_room);

        searchResult = (ListView) findViewById(R.id.searchResult);
        notFoundChatRoom = (TextView) findViewById(R.id.notFoundChatRoom);

        controllerHall = HallActivity.controllerHall;

        controllerHall.searchChatRoom(SearchChatRoomActivity.this, getSearchText());

    }

    public static void setSearchText(String searchText){
        if(searchText.trim().length() == 0) return;
        if(searchText.length() > 20) return;

        SearchChatRoomActivity.searchText = searchText;
    }

    public static String getSearchText(){
        return SearchChatRoomActivity.searchText;
    }
}