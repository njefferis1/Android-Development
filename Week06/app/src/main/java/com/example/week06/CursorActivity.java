package com.example.week06;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class CursorActivity extends AppCompatActivity
{
    static final String TAG = "CursorActivity";
    DBManager dbManager;
    SQLiteDatabase database;
    Cursor cursor;
    ListView listView;
    DBAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursor);
        listView = findViewById(R.id.list_view_cursor);
        dbManager = new DBManager(this);
        database = dbManager.getReadableDatabase();
    }

    @Override
    protected void onDestroy()
    {
        database.close();
        super.onDestroy();
    }

    @Override
    protected void onResume()
    {
        cursor = database.query(DBManager.TABLE_NAME, null, null, null, null, null, DBManager.C_ID + " DESC");
        startManagingCursor(cursor);
        adapter = new DBAdapter(this, cursor);
        listView.setAdapter(adapter);
        Log.d(TAG, "in onResume");
        super.onResume();
    }
}
