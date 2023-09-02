package com.match.onlinechat.model.basic.user;

import static com.alibaba.fastjson.JSON.toJSONString;

import android.content.Context;
import android.content.SharedPreferences;

import com.match.onlinechat.model.basic.client.Client;
import com.match.onlinechat.model.basic.tools.GeneratingTools;

import java.util.HashMap;

public class SaveUserInfo {

    private static String userInfoFileName;
    private static Context context;
    private Client client;

    public SaveUserInfo(){}

    public SaveUserInfo(String userInfoFileName, Context context){
        SaveUserInfo.userInfoFileName = userInfoFileName;
        SaveUserInfo.context = context;
    }

    public void save(String userName, int userId){
        SharedPreferences sp = SaveUserInfo.context.getSharedPreferences(
                SaveUserInfo.userInfoFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USER_NAME", userName);
        editor.putInt("USER_ID", userId);
        editor.commit();
    }

    public HashMap<String, String> get(){
        SharedPreferences sp = SaveUserInfo.context.getSharedPreferences(
                SaveUserInfo.userInfoFileName, Context.MODE_PRIVATE);
        HashMap<String, String> userInfo = new HashMap<>();
        String userName = sp.getString("USER_NAME", null);
        int userId = sp.getInt("USER_ID", 10000);

        userInfo.put("USER_NAME", userName);
        userInfo.put("USER_ID", String.valueOf(userId));

        return userInfo;
    }

    public void logOff(Client client){
        //先向服务器提交注销申请
        GeneratingTools generatingTools = new GeneratingTools();
        generatingTools.json("mode", "logOff");
        generatingTools.json("id", new User().getId());
        try {
            client.send(toJSONString(generatingTools.getJson()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //本地注销
        SharedPreferences sp = SaveUserInfo.context.getSharedPreferences(
                SaveUserInfo.userInfoFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
