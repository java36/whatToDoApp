package com.example.sinas.whattodoapp;

import android.database.Cursor;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{
    private Long id;
    private String name;
    private String username;
    private String password;

    public User(Long id, String name, String username, String password){
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Username: %s, Password: %s", name, username, password);
    }

    public static User getUserFromCursor(Cursor cursor){

        Long id = cursor.getLong(cursor.getColumnIndex("id"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String username = cursor.getString(cursor.getColumnIndex("username"));
        String password = cursor.getString(cursor.getColumnIndex("password"));

        return new User(id, name, username, password);
    }
}
