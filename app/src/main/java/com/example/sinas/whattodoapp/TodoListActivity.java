package com.example.sinas.whattodoapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TodoListActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 123;
    private Toolbar toolbar;
    private ListView listView;
    private Long userId;
    private String name;
    private List<Todo> todoList;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        userId = intent.getLongExtra("userId", 0L);
        name = intent.getStringExtra("name");

        setTitle(name + "'s Todo List");

        databaseHelper = DatabaseHelper.newInstance(this);
        database = databaseHelper.getReadableDatabase();
        todoList = getTodoListFromDB(userId);
        listView = (ListView) findViewById(R.id.listView);
        populateListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            finish();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_add) {
            Intent intent = new Intent(getApplicationContext(), AddTodoActivity.class);
            intent.putExtra("userId", userId);
            startActivityForResult(intent, REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private List<Todo> getTodoListFromDB(Long uId){

        List<Todo> todos = new ArrayList<>();
        String query = "SELECT * FROM todo where user_id = " + uId;

        if(database != null){

            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()){
                while(!cursor.isAfterLast()){
                    todos.add(Todo.getTodoFromCursor(cursor));
                    cursor.moveToNext();
                }
            }

            cursor.close();
        }

        return todos;
    }

    private void populateListView(){
        final ArrayAdapter<Todo> arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, todoList);
        listView.setAdapter(arrayAdapter);
    }
}