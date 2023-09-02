package com.match.onlinechat.application;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.match.onlinechat.activity.HallActivity;
import com.match.onlinechat.model.basic.client.Client;
import com.match.onlinechat.model.basic.constants.ClientConst;
import com.match.onlinechat.model.basic.constants.DefaultConfigValues;
import com.match.onlinechat.model.basic.constants.InitErrorInfoConst;
import com.match.onlinechat.model.basic.exception.ExceptionHandler;
import com.match.onlinechat.model.basic.user.User;
import com.match.onlinechat.model.basic.user.SaveUserInfo;

import java.net.SocketException;
import java.util.HashMap;
import java.util.Objects;

/*启动全局异常处理线程和获取用户登录的状态*/
public class OnlineChatApplication extends Application {

    private User user;
    public static Client client;

    @Override
    public void onCreate(){
        this.init();
        super.onCreate();

    }

    public void init()  {
        //app初始化
        user = new User();
        ExceptionHandler exceptionHandler = ExceptionHandler.getInstance();
        exceptionHandler.init(getApplicationContext()); //设置异常处理器
        System.out.println("set exceptionHandler finish");
        //判断获取用户登录信息
        SaveUserInfo saveUserInfo = new SaveUserInfo("UserInfo", getApplicationContext());
        HashMap<String, String> userInfo = saveUserInfo.get();
        String userName = userInfo.get("USER_NAME");
        int userId = Integer.parseInt(Objects.requireNonNull(userInfo.get("USER_ID")));
        if(userName != null) {
            user.setName(userName);
            user.setId(userId);
        }
        System.out.println("get userInfo finish");
        //初始化网络
        try {
            Thread initThread = new Thread(this::initNetWork);
            initThread.start();
            initThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            this.initErrorPrompt(InitErrorInfoConst.INIT_NETWORK_ERROR);
        }
        System.out.println("init finish");
    }

    private void initNetWork(){
        try {
            client = new Client(DefaultConfigValues.IP,
                    DefaultConfigValues.PORT);
            client.initClient();
        }catch (SocketException | InterruptedException e){
            e.printStackTrace();
            this.initErrorPrompt(InitErrorInfoConst.INIT_NETWORK_ERROR);
        }
        System.out.println("network init finish");
    }

    public void initErrorPrompt(String errorPrompt){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), errorPrompt,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
