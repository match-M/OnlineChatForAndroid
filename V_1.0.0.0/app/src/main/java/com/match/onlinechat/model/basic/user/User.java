package com.match.onlinechat.model.basic.user;

public class User {
    private static String name = null;
    private static int id = 10000;

    public String getName() {
        return name;
    }

    public void setName(String name) { User.name = name; }

    public int getId() {
        return id;
    }

    public void setId(int id) { User.id = id; }
}
