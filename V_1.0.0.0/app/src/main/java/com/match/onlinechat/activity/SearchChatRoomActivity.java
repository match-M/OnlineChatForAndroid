package com.match.onlinechat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.match.onlinechat.R;
import com.match.onlinechat.controller.ControllerHall;

public class SearchChatRoomActivity extends Activity {

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

        searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String chatRoomName = getSearchText();
                if (chatRoomName != null) {
                    controllerHall.selectChatRoom(chatRoomName);
                    Intent intent = new Intent();
                    intent.setClass(SearchChatRoomActivity.this, ChatActivity.class);
                    //把聊天室名字穿给ChatActivity
                    Bundle bundle = new Bundle();
                    bundle.putString("chatRoomName", chatRoomName);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

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