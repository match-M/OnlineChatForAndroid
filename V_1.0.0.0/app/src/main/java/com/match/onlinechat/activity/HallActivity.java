package com.match.onlinechat.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.match.onlinechat.application.OnlineChatApplication;
import com.match.onlinechat.model.adapter.list.ChatRoomListAdapter;
import com.match.onlinechat.model.basic.client.Client;
import com.match.onlinechat.controller.ControllerHall;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.match.onlinechat.R;
import com.match.onlinechat.model.basic.constants.SignupPrompt;
import com.match.onlinechat.model.basic.constants.UserOperationError;
import com.match.onlinechat.model.basic.user.SaveUserInfo;
import com.match.onlinechat.model.basic.user.User;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HallActivity extends AppCompatActivity {


    public static Client client;
    public static ListView chatRoomList;
    public static ChatRoomListAdapter adapter;
    public static ControllerHall controllerHall;

    private User user;
    private static Timer timer;
    private static Thread updateChatRoomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);

        user = new User();
        client = OnlineChatApplication.client;

        chatRoomList = (ListView) findViewById(R.id.chatRoomList);

        controllerHall = new ControllerHall();
        controllerHall.init();

        chatRoomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String chatRoomName = adapter.getItem(i);
                if (chatRoomName != null) {
                    chatRoomName = chatRoomName.substring(0, chatRoomName.indexOf("("));
                    controllerHall.selectChatRoom(chatRoomName);
                    Intent intent = new Intent();
                    intent.setClass(HallActivity.this, ChatActivity.class);
                    //把聊天室名字穿给ChatActivity
                    Bundle bundle = new Bundle();
                    bundle.putString("chatRoomName", chatRoomName);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        menu.add(0, 0, 3, "个人信息"); //添加新的菜单
        menu.add(0, 1, 4 , "注销"); //添加新的菜单
        if(user.getName() != null){
            this.isSignupMode(menu);
        }else{
           this.notSignupMode(menu);
        }
        this.setSearch(menu); //设置搜索按钮
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);
        if(user.getName() == null){
            this.notSignupMode(menu);
        }else{
           this.isSignupMode(menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.signup: {
                if (user.getName() != null) {
                    Toast.makeText(HallActivity.this, SignupPrompt.MULTIPLE_REGISTRATION,
                            Toast.LENGTH_LONG).show();
                    break;
                }
                startActivity(new Intent(HallActivity.this, SignupActivity.class));
                break;
            }
            case R.id.newChatRoom: {
                if (user.getName() == null) {
                    Toast.makeText(HallActivity.this, UserOperationError.UNREGISTERED,
                            Toast.LENGTH_LONG).show();
                    break;
                }
                startActivity(new Intent(HallActivity.this, NewChatRoomActivity.class));
                break;
            }
            case 0:{
                startActivity(new Intent(HallActivity.this, ShowUserInfoActivity.class));
                break;
            }
            case 1:{
                new SaveUserInfo().logOff(client); //注销账号
                //初始化User类的状态
                user.setName(null);
                user.setId(10000);
                Toast.makeText(HallActivity.this, "拜拜了您嘞！",
                        Toast.LENGTH_SHORT).show();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    public void setSearch(Menu menu){
        MenuItem menuItem = menu.findItem(R.id.searchChatRoom);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("搜索你想加入的聊天室吧！"); //设置提示词
        searchView.setSubmitButtonEnabled(true); //设置提交按钮可以显示
        //设置监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchText) {
                SearchChatRoomActivity.setSearchText(searchText);
                startActivity(new Intent(HallActivity.this, SearchChatRoomActivity.class));
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
    }

    public void getChatRoom(){
        //采用定时任务，定时刷新房间列表
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                controllerHall.getChatRoomList();
                ArrayList<String> list = ControllerHall.list;
                adapter = new ChatRoomListAdapter(HallActivity.this,
                        android.R.layout.simple_list_item_1,
                        list);
            }
        };
        //运行
        timer.schedule(timerTask, 0 ,2000); //延迟0秒，间隔2秒循环执行
        HallActivity.timer = timer;
    }

    public void isSignupMode(Menu menu){
        menu.findItem(R.id.signup).setVisible(false);
        //设置这些菜单选项可见
        menu.findItem(0).setVisible(true);
        menu.findItem(1).setVisible(true);
    }

    public void notSignupMode(Menu menu){
        menu.findItem(R.id.signup).setVisible(true);
        //设置这些菜单选项不可见
        menu.findItem(0).setVisible(false);
        menu.findItem(1).setVisible(false);
    }

    @Override
    protected void onPause(){
        super.onPause();
        //当大厅界面隐藏或不可见的时候执行一下任务
        HallActivity.timer.cancel(); //取消定时任务
        updateChatRoomList.interrupt(); //中断线程
    }

    @Override
    protected void onResume(){
        super.onResume();
        //当大厅界面可以见时再次开启线程
        updateChatRoomList = new Thread(this::getChatRoom);
        updateChatRoomList.start();
    }

    @Override
    protected void onDestroy(){
        controllerHall.sendMessage("exit_mobile"); //告诉服务端，退出app
        System.out.println("onDestroy");
        super.onDestroy();
    }

}