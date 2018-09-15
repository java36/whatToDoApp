package com.example.sinas.whattodoapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHelper = DatabaseHelper.newInstance(this);
        database = databaseHelper.getReadableDatabase();

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final TextView tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        final Button bLogin = (Button) findViewById(R.id.bSignIn);

        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                login(username, password);
            }
        });
    }

    private List<User> getAllUsersFromDB(){

        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM user";

        if(database != null){

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){
                while(!cursor.isAfterLast()){
                    users.add(User.getUserFromCursor(cursor));
                    cursor.moveToNext();
                }
            }

            cursor.close();
        }

        return users;
    }

    public void login(String username, String password){

        userList = getAllUsersFromDB();

        for(int i=0; i<userList.size(); i++){
            User user = userList.get(i);
            if(user.getUsername().equals(username)){
                if(user.getPassword().equals(password)) {

                    Intent intent = new Intent(LoginActivity.this, TodoListActivity.class);
                    intent.putExtra("userId", user.getId());
                    intent.putExtra("name", user.getName());
                    LoginActivity.this.startActivity(intent);
                    break;
                }
                else {
                    makeToast("Wrong password");
                    break;
                }
            }
            if(i == userList.size()-1 && !user.getUsername().equals(username)){
                makeToast("User not found");
            }
        }
    }

    private void makeToast(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
