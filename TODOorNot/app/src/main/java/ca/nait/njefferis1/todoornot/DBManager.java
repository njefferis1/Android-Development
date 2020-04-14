package ca.nait.njefferis1.todoornot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper
{

    static final String TAG = "DBManager";
    static final String DB_NAME = "todo.db";
    static final int DB_VERSION = 7;

    static final String TITLE_TABLE_NAME = "ListTitles";
    static final String C_TITLE_ID = BaseColumns._ID;
    static final String C_TITLE_DESCRIPTION = "title_description";

    static final String ITEM_TABLE_NAME = "ListItems";
    static final String C_ITEM_ID = "item_id";
    static final String C_ITEM_DESCRIPTION = "item_description";
    static final String C_CREATED_DATE = "created_date";
    static final String C_FLAG = "false";

    public DBManager(Context context) {super(context, DB_NAME, null, DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        //String sql1 = "create table " + TITLE_TABLE_NAME + " (" + C_TITLE_ID + " int primary key autoincrement, " + C_TITLE_DESCRIPTION + " text)";
        String sql1 = String.format("create table %s (%s integer primary key autoincrement, %s text) ", TITLE_TABLE_NAME, C_TITLE_ID, C_TITLE_DESCRIPTION);
        Log.d(TAG, "sql = " + sql1);
        database.execSQL(sql1);

        //String sql2 = " create table " + ITEM_TABLE_NAME + " (" + C_ITEM_ID + " int primary key autoincrement, " + C_TITLE_ID + " int, " + C_ITEM_DESCRIPTION + " text, " + C_CREATED_DATE + " text, " + C_FLAG + " boolean)";
        String sql2 = String.format("create table %s (%s integer primary key autoincrement, %s integer, %s text, %s text, %s text)",
                        ITEM_TABLE_NAME, C_ITEM_ID, C_TITLE_ID, C_ITEM_DESCRIPTION, C_CREATED_DATE, C_FLAG);
        Log.d(TAG, "sql = " + sql2);
        database.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        database.execSQL("drop table if exists " + TITLE_TABLE_NAME);
        database.execSQL("drop table if exists " + ITEM_TABLE_NAME);
        Log.d(TAG, "in onUpgraded");
        onCreate(database);
    }

    public void updateItem(String item)
    {
        String itemID = getItemID(item);
        ContentValues values = new ContentValues();
        values.put(DBManager.C_ITEM_DESCRIPTION, item);

        SQLiteDatabase database = this.getWritableDatabase();
        database.update(DBManager.ITEM_TABLE_NAME, values, DBManager.C_ITEM_ID + "=" + "'" + itemID + "'", null);
        database.close();
    }

    public void deleteItem(String item)
    {
        String itemID = getItemID(item);

        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(DBManager.ITEM_TABLE_NAME, DBManager.C_ITEM_ID + "=" + itemID, null);
        database.close();
    }

    public void insertListTitle(String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(C_TITLE_DESCRIPTION, title);

        db.insertOrThrow(TITLE_TABLE_NAME, null, values);
        db.close();
    }

    public void insertListItem(String item, String title, String date, String completed)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(C_TITLE_ID, getTitleID(title));
        values.put(C_ITEM_DESCRIPTION, item);
        values.put(C_CREATED_DATE, date);
        values.put(C_FLAG, completed);

        db.insertOrThrow(ITEM_TABLE_NAME, null, values);
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

    public String getTitleID(String title)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String titleID = "";

        String whereClause = DBManager.C_TITLE_DESCRIPTION + "=" + "'" + title + "'";
        Cursor cursor = db.query(DBManager.TITLE_TABLE_NAME, null, whereClause, null, null, null, null);
        //Cursor titleID = db.rawQuery("Select " + DBManager.C_TITLE_ID + " from " + DBManager.TITLE_TABLE_NAME + " where " + whereClause, null);

        while(cursor.moveToNext())
        {
            titleID = cursor.getString(0);
        }


        //db.close();
        cursor.close();

        return titleID;
    }

    public String getItemID(String item)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String itemID = "";

        String whereClause = DBManager.C_ITEM_DESCRIPTION + "=" + "'" + item + "'";
        Cursor cursor = db.query(DBManager.ITEM_TABLE_NAME, null, whereClause, null, null, null, null);

        while(cursor.moveToNext())
        {
            itemID = cursor.getString(0);
        }

        //db.close();

        return itemID;
    }

    public List<String> getItems(String title)
    {
        List<String> items = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();

        String titleID = getTitleID(title);

        String whereClause = DBManager.C_TITLE_ID + "=" + (titleID);
        Cursor cursor = db.query(DBManager.ITEM_TABLE_NAME, null, whereClause, null, null, null, null);

        if(cursor.moveToFirst())
        {
            do
            {
                items.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return items;
    }
}
