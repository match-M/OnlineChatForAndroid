package com.match.onlinechat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.match.onlinechat.R;
import com.match.onlinechat.controller.ControllerHall;
import com.match.onlinechat.model.adapter.message.MessageAdapter;
import com.match.onlinechat.model.basic.chat.ClientHandler;
import com.match.onlinechat.model.basic.chat.message.ChatMessage;

public class ChatActivity extends Activity {

    public Toolbar toolbar;
    public Button btn_send;
    public EditText et_inputMessage;
    public ControllerHall controllerHall;
    public static ListView lv_messageList;
    public static MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_chat);
        controllerHall = HallActivity.controllerHall;

        toolbar = (Toolbar) findViewById(R.id.chat_room_name);
        btn_send = (Button) findViewById(R.id.send);
        lv_messageList = (ListView) findViewById(R.id.show_chat_message);
        et_inputMessage = (EditText) findViewById(R.id.input_message);
        //获取聊天室名字并设置为toolbar的标题
        toolbar.setTitle(intent.getStringExtra("chatRoomName"));
        controllerHall.setChatActivity(ChatActivity.this);
        btn_send.setVisibility(View.GONE);

        //监听文本输入框是否有变化
        et_inputMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence inputMessage, int start, int before, int count) {
                //判断文本去除空格后是否为空，为空则不出现发送按钮
                if(inputMessage.toString().trim().length() == 0){
                    btn_send.setVisibility(View.GONE);
                    btn_send.setEnabled(false);
                    return;
                }
                btn_send.setVisibility(View.VISIBLE);
                btn_send.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = et_inputMessage.getText().toString();
                controllerHall.sendMessage(message);
                et_inputMessage.setText("");

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setUserName("MY"); //标识是自己发送的消息
                chatMessage.setMessage(message);
                ClientHandler.chatMessages.add(chatMessage);
                controllerHall.showMessage(ClientHandler.chatMessages);
            }
        });

    }

    @Override
    protected void onPause(){
        try {
            controllerHall.sendMessage("exit(room);");
            ClientHandler.chatMessages.clear();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        super.onPause();
    }
}