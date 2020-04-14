package ca.nait.njefferis1.todoornot;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpinnerListener extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    ArrayList<HashMap<String, String>> todoItem = new ArrayList<>();
    public static final String ITEM_ID = "ItemId";
    public static final String ITEM = "Item";
    List<String> items = new ArrayList();

    DBManager dbManager;
    SQLiteDatabase database;
    Cursor cursor;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {

//        populateList(parent.getItemAtPosition(position).toString());
//        //does this after being selected
//        DBManager db = new DBManager(getApplicationContext());
//
//        List<String> titles = db.getItems(parent.getItemAtPosition(position).toString());
//        if(!titles.isEmpty())
//        {
//            //populate list view here
//            items.clear();
//
//            String titleID; //= db.getTitleID(spinner.getSelectedItem().toString());
//            Spinner spinner = findViewById(R.id.spinner_list_title);
//            ListView listView = findViewById(R.id.list_view_items);
//            //DBManager db = new DBManager(getApplicationContext());
//            database = db.getReadableDatabase();
//
//            if(spinner.getSelectedItem() != null)
//            {
//                titleID = db.getTitleID(spinner.getSelectedItem().toString());
//
//
//                String whereClause = DBManager.C_TITLE_ID + "=" + "'" + titleID + "'";
//                Cursor cursor = database.query(DBManager.ITEM_TABLE_NAME, null, whereClause, null, null, null, null);
//
//                if(cursor.moveToNext())
//                {
//                    do
//                    {
//                        items.add(cursor.getString(2));
//                    } while (cursor.moveToNext());
//                }
//                cursor.close();
//                //database.close();
//
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, items);
//
//                listView.setAdapter(adapter);
//            }
//        }
//        else
//        {
//            //no list items yet
//        }
    }

    private void populateList(String selection)
    {
        items.clear();

        String titleID; //= db.getTitleID(spinner.getSelectedItem().toString());
        Spinner spinner = findViewById(R.id.spinner_list_title);
        ListView listView = findViewById(R.id.list_view_items);
        DBManager db = new DBManager(getApplicationContext());
        database = db.getReadableDatabase();

        if(spinner.getSelectedItem() != null)
        {
            titleID = db.getTitleID(spinner.getSelectedItem().toString());


            String whereClause = DBManager.C_TITLE_ID + "=" + "'" + titleID + "'";
            Cursor cursor = database.query(DBManager.ITEM_TABLE_NAME, null, whereClause, null, null, null, null);

            if(cursor.moveToNext())
            {
                do
                {
                    items.add(cursor.getString(2));
                } while (cursor.moveToNext());
            }
            cursor.close();
            //database.close();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, items);

            listView.setAdapter(adapter);
        }

//        try
//        {
//            DBManager db = new DBManager(getApplicationContext());
//            //List<String> items = new ArrayList<String>();
//
//            database = db.getReadableDatabase();
//
//            String titleID = db.getTitleID(selection);
//
//            String whereClause = DBManager.C_TITLE_ID + "=" + (titleID);
//            Cursor cursor = database.query(DBManager.ITEM_TABLE_NAME, null, whereClause, null, null, null, null);
//
//            if(cursor.moveToFirst())
//            {
//                do
//                {
//                    HashMap<String, String> tempMap = new HashMap<String, String>();
//
//                    tempMap.put(ITEM_ID, cursor.getString(0));
//                    tempMap.put(ITEM, cursor.getString(2));
//
//                    todoItem.add(tempMap);
//                } while (cursor.moveToNext());
//            }
//            cursor.close();
//            database.close();
//        }
//        catch (Exception e)
//        {
//            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
//        }
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //@Override
    //public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    //{
        //EditText et = findViewById(R.id.et_list_edit_item);
        //et.setText(items.get(position)); //set text to selected item from list view

        //does this after being selected
//        DBManager db = new DBManager(getApplicationContext());
//
//        List<String> titles = db.getItems(parent.getItemAtPosition(position).toString());
//        if(!titles.isEmpty())
//        {
//            //populate list view here
//            //String[] keys = new String[]{ITEM_ID, ITEM};
//            //int[] ids = new int[]{R.id.custom_row_text_view_item_id, R.id.custom_row_text_view_item};
//            String[] keys = new String[]{ITEM};
//            int[] ids = new int[]{R.id.custom_row_text_view_item};
//
//            SimpleAdapter adapter = new SimpleAdapter(this, todoItem, R.layout.custom_list_view_row, keys, ids);
//
//            //get a list of all items associated with selected title
//            populateList(parent.getItemAtPosition(position).toString());
//
//            ListView lv = findViewById(R.id.list_view_items);
//
//            lv.setAdapter(adapter);
//        }
//        else
//        {
//            //no list items yet
//        }
    //}
}
