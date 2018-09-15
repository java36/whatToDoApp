package com.example.sinas.whattodoapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddTodoActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        setTitle("Add Todo");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseHelper = DatabaseHelper.newInstance(this);
        database = databaseHelper.getWritableDatabase();

        Intent intent = getIntent();
        userId = intent.getLongExtra("userId", 0L);

        final EditText contentEditText = findViewById(R.id.content_et);
        Button saveButton = findViewById(R.id.save_btn);

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String newEntry = contentEditText.getText().toString();
                if (contentEditText.length() != 0) {
                    addNewTodo(newEntry, userId);
                    contentEditText.setText("");
                    Intent intent = new Intent(AddTodoActivity.this, TodoListActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                } else {
                    toastMessage("You must write something in the text field!");
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            Intent intent = new Intent(getApplicationContext(), TodoListActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void addNewTodo(String content, Long userId) {
        if(database != null){

            ContentValues values = new ContentValues();
            values.put("content", content);
            values.put("user_Id", userId);
            try{
                database.insertOrThrow("todo", null, values);
                Log.d("Success", "todo successfully added");
            }
            catch(SQLException e){
                Log.d("Error", "Error saving todo");
            }
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}
