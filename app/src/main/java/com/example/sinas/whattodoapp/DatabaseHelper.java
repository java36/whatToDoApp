package com.example.sinas.whattodoapp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todo_list.db";
    private static final int DATABASE_VERSION = 1;
    private static final String USER_TABLE_QUERY = "CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT, username TEXT, password TEXT)";
    private static final String TODO_TABLE_QUERY = "create table todo (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "content TEXT, user_id INTEGER, FOREIGN KEY (user_id) REFERENCES user(id))";

    private static DatabaseHelper dbInstance = null;
    private Context mContext;

    public static DatabaseHelper newInstance(Context context){
        if(dbInstance == null){
            dbInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return dbInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(USER_TABLE_QUERY);
            sqLiteDatabase.execSQL(TODO_TABLE_QUERY);
        }
        catch(SQLException e){
            Log.d("Error", "Error creating database " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }
}
