package com.match.onlinechat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.match.onlinechat.R;
import com.match.onlinechat.controller.ControllerHall;

public class NewChatRoomActivity extends Activity {

    public Button finish;
    public EditText newChatRoomName;
    public String name;
    public ControllerHall controllerHall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat_room);

        finish = (Button) findViewById(R.id.finish);
        newChatRoomName = (EditText) findViewById(R.id.newChatRoomName);

        controllerHall = IHallActivity.controllerHall;

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name =  newChatRoomName.getText().toString();
                controllerHall.newChatRoom(NewChatRoomActivity.this, name);
            }
        });
    }
}