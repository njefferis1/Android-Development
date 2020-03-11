package ca.nait.njefferis1.todoornot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper
{

    static final String TAG = "DBManager";
    static final String DB_NAME = "todo.db";
    static final int DB_VERSION = 3;
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
        String sql1 = "create table " + TITLE_TABLE_NAME + " (" + C_TITLE_ID + " int primary key, " + C_TITLE_DESCRIPTION + " text)";
        Log.d(TAG, "sql = " + sql1);
        database.execSQL(sql1);

        /*String sql2 = " create table " + ITEM_TABLE_NAME + " (" + C_ITEM_ID + " int primary key autoincrement, " + C_TITLE_ID + " text, " + C_ITEM_DESCRIPTION + " text, " + C_CREATED_DATE + " text, " + C_FLAG + " text)";
        Log.d(TAG, "sql = " + sql2);
        database.execSQL(sql2);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        database.execSQL("drop table if exists " + TITLE_TABLE_NAME);
        database.execSQL("drop table if exists " + ITEM_TABLE_NAME);
        Log.d(TAG, "in onUpgraded");
        onCreate(database);
    }

    public void insertListTitle(String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(C_TITLE_DESCRIPTION, title);

        db.insert(TITLE_TABLE_NAME, null, values);
        db.close();
    }

    public List<String> getAllTitles()
    {
        List<String> titles = new ArrayList<String>();

        String selectStatment = "SELECT * FROM " + TITLE_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectStatment, null);

        if(cursor.moveToFirst())
        {
            do
            {
                titles.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return titles;
    }
}
