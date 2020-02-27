package com.example.week06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class CursorActivity extends BaseActivity
{
    static final String TAG = "CursorActivity";
    ListView listView;
    DBAdapter adapter;
    DBManager dbManager;
    SQLiteDatabase database;
    Cursor cursor;
    ChatReceiver receiver;
    IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursor);
        listView = findViewById(R.id.list_view_cursor);
        dbManager = new DBManager(this);
        database = dbManager.getReadableDatabase();
        receiver = new ChatReceiver();
        filter = new IntentFilter(ChatService.NEW_MESSAGE_RECEIVED);
    }

    @Override
    protected void onResume()
    {
        cursor = database.query(DBManager.TABLE_NAME, null, null, null, null, null, DBManager.C_ID + " DESC");
        startManagingCursor(cursor);

        adapter = new DBAdapter(this, cursor);
        listView.setAdapter(adapter);

        registerReceiver(receiver, filter);

        Log.d(TAG, "in onResume()");
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        unregisterReceiver(receiver);
        super.onPause();
    }

    class ChatReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            cursor.requery();
            adapter.notifyDataSetChanged();
            Log.d(TAG, " received a " + ChatService.NEW_MESSAGE_RECEIVED);
        }
    }
}













