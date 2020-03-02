package com.example.week06;

import android.app.Fragment;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentListView extends Fragment
{
    static final String TAG = "FragmentListView";
    ListView listView;
    DBAdapter adapter;
    DBManager dbManager;
    SQLiteDatabase database;
    Cursor cursor;
    CursorActivity.ChatReceiver receiver;
    IntentFilter filter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        listView = view.findViewById(R.id.fragment_lv);
        dbManager = new DBManager(getActivity());
        database = dbManager.getReadableDatabase();
        return view;
    }

    @Override
    public void onResume()
    {
        cursor = database.query(DBManager.TABLE_NAME, null, null, null, null, null, DBManager.C_ID + " DESC");
        getActivity().startManagingCursor(cursor);

        adapter = new DBAdapter(getActivity(), cursor);

        listView.setAdapter(adapter);

        Log.d(TAG, "in onResume()");

        super.onResume();
    }
}
