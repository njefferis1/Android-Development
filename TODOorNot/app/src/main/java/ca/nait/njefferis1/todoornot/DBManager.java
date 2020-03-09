package ca.nait.njefferis1.todoornot;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DBManager extends SQLiteOpenHelper
{

    static final String TAG = "DBManager";
    static final String DB_NAME = "todo.db";
    static final int DB_VERSION = 1;
    static final String TITLE_TABLE_NAME = "ListTitles";
    static final String C_TITLE_ID = BaseColumns._ID;
    static final String C_TITLE_DESCRIPTION = "title_description";
    static final String ITEM_TABLE_NAME = "ListItems";
    static final String C_ITEM_ID = BaseColumns._ID;
    static final String C_ITEM_DESCRIPTION = "item_description";
    static final String C_CREATED_DATE = "created_date";
    static final boolean C_FLAG = false;

    public DBManager(Context context) {super(context, DB_NAME, null, DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        String sql = "create table " + TITLE_TABLE_NAME + " (" + C_TITLE_ID + " int primary key, " + " text, + C_TITLE_DESCRIPTION + text)";
        Log.d(TAG, "sql = " + sql);

        sql = "create table " + ITEM_TABLE_NAME + " (" + C_ITEM_ID + " int primary key, " + C_ITEM_DESCRIPTION + " text, " + C_CREATED_DATE + " text, " + C_FLAG + " text)";
        Log.d(TAG, "sql = " + sql);

        database.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        database.execSQL("drop table if exists " + TITLE_TABLE_NAME);
        database.execSQL("drop table if exists " + ITEM_TABLE_NAME);
        Log.d(TAG, "in onUpgraded");
        onCreate(database);
    }
}
