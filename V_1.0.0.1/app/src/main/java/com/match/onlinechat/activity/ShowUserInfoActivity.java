package com.match.onlinechat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.match.onlinechat.R;
import com.match.onlinechat.model.basic.user.User;

public class ShowUserInfoActivity extends Activity {

    private TextView userName;
    private TextView userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_info);

        userName = (TextView) findViewById(R.id.userName);
        userId = (TextView) findViewById(R.id.userId);

        User user = new User();

        userName.setText("用户名:"+user.getName());
        userId.setText("用户ID:"+user.getId());
    }
}