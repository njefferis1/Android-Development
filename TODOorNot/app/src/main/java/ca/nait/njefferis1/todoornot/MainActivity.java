package ca.nait.njefferis1.todoornot;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener
{
    static final String TAG = "MainActivity";

    DBManager dbManager;
    SQLiteDatabase database;
    Cursor cursor;
    List<String> items = new ArrayList();
    ArrayList<HashMap<String, String>> todoItem = new ArrayList<>();
    public static final String ITEM_ID = "ItemId";
    public static final String ITEM = "Item";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DBManager(this);

        ArrayList<String> spinnerArray = new ArrayList<String>();
        populateSpinner();
        Spinner spinner = findViewById(R.id.spinner_list_title);
        spinner.setOnItemSelectedListener(new SpinnerListener(this));

        Button saveListNameButton = findViewById(R.id.button_save_list_title);
        saveListNameButton.setOnClickListener(this);

        Button saveListItemButton = findViewById(R.id.button_save_list_item);
        saveListItemButton.setOnClickListener(this);

        Button updateListItemButton = findViewById(R.id.button_item_update);
        updateListItemButton.setOnClickListener(this);

        Button deleteListItemButton = findViewById(R.id.button_item_delete);
        deleteListItemButton.setOnClickListener(this);

        ListView listView = findViewById(R.id.list_view_items);
        listView.setOnItemClickListener(this);

        if(spinner.getSelectedItem() != null)
        {
            populateList(spinner.getSelectedItem().toString());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflator = this.getMenuInflater();
        inflator.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case(R.id.menu_item_preferences):
            {
                Intent intent = new Intent(this, SettingsActivity.class);
                this.startActivity(intent);
                break;
            }
        }
        return true;
    }

    private class SpinnerListener implements AdapterView.OnItemSelectedListener
    {
        MainActivity activity;

        public SpinnerListener(MainActivity context)
        {
            activity = (MainActivity)context;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int index, long id)
        {
            Spinner listTitle = findViewById(R.id.spinner_list_title);
            if (listTitle.getSelectedItem() != null)
            {
                String title = listTitle.getSelectedItem().toString();
                populateList(title);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        int n = 1;
        switch (v.getId())
        {
            case R.id.button_save_list_title:
            {
                EditText listTitleTB = findViewById(R.id.et_list_title);
                String listTitle = listTitleTB.getText().toString();

                if(listTitle.trim().length() > 0)
                {
                    DBManager db = new DBManager(getApplicationContext());
                    db.insertListTitle(listTitle);
                    listTitleTB.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(listTitleTB.getWindowToken(), 0);
                    populateSpinner();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please enter List Title",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.button_save_list_item:
            {
                EditText listItemTB = findViewById(R.id.et_list_item);
                String listItem = listItemTB.getText().toString();
                Spinner listTitle = findViewById(R.id.spinner_list_title);
                String title = listTitle.getSelectedItem().toString();
                String now = new Date().toString();

                if(listItem.trim().length() > 0)
                {
                    DBManager db = new DBManager(getApplicationContext());
                    db.insertListItem(listItem, title, now, "false");
                    listItemTB.setText("");
                    populateList(title);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(listItemTB.getWindowToken(), 0);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please enter a List Item",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.button_item_update:
            {
                EditText selectedItemTB = findViewById(R.id.et_list_edit_item);
                String selectedItem = selectedItemTB.getText().toString();
                Spinner listTitle = findViewById(R.id.spinner_list_title);
                String title = listTitle.getSelectedItem().toString();

                if(selectedItem.trim().length() > 0)
                {
                    DBManager db = new DBManager(getApplicationContext());
                    db.updateItem(selectedItem);
                    selectedItemTB.setText("");
                    populateList(title);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please select a List Item",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.button_item_delete:
            {
                EditText selectedItemTB = findViewById(R.id.et_list_edit_item);
                String selectedItem = selectedItemTB.getText().toString();
                Spinner listTitle = findViewById(R.id.spinner_list_title);
                String title = listTitle.getSelectedItem().toString();

                if(selectedItem.trim().length() > 0)
                {
                    DBManager db = new DBManager(getApplicationContext());
                    db.deleteItem(selectedItem);
                    selectedItemTB.setText("");
                    populateList(title);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please select List Item",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void populateSpinner()
    {
        //ArrayList<String> spinnerArray = new ArrayList<String>();
        Spinner spinner = findViewById(R.id.spinner_list_title);
        try
        {
            DBManager db = new DBManager(getApplicationContext());

            List<String> titles = db.getAllTitles();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, titles);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
        catch(Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        EditText et = findViewById(R.id.et_list_edit_item);
        et.setText(items.get(position)); //set text to selected item from list view
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

}
