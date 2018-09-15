package com.example.sinas.whattodoapp;

import android.database.Cursor;

import java.io.Serializable;

public class Todo implements Serializable {

    private Long id;
    private String content;
    private Long userId;

    public Todo(Long id, String content , Long userId){
        this.id = id;
        this.content = content;
        this.userId = userId;
    }

    public String getContent(){
        return content;
    }

    public Long getId(){
        return id;
    }

    public Long getUserId(){
        return userId;
    }

    @Override
    public String toString() {
        return content;
    }

    public String getTodoInfo(){
        return String.format("Content: %s UserId: %s", content, userId);
    }

    public static Todo getTodoFromCursor(Cursor cursor){

        Long id = cursor.getLong(cursor.getColumnIndex("id"));
        String content = cursor.getString(cursor.getColumnIndex("content"));
        Long userId = cursor.getLong(cursor.getColumnIndex("user_id"));

        return new Todo(id, content, userId);
    }
}
