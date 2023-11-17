package com.match.onlinechat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.match.onlinechat.R;
import com.match.onlinechat.controller.ControllerHall;

public class SignupActivity extends Activity {

    public Button finish;
    public EditText userName;

    public String name;
    public ControllerHall controllerHall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        finish = (Button) findViewById(R.id.finish);
        userName = (EditText) findViewById(R.id.userName);

        controllerHall = IHallActivity.controllerHall;

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = userName.getText().toString();
                controllerHall.signup(SignupActivity.this, name);
            }
        });


    }
}