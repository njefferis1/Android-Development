package ca.nait.njefferis1.todoornot;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    static final String TAG = "MainActivity";

    DBManager dbManager;
    SQLiteDatabase database;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DBManager(this);

        ArrayList<String> spinnerArray = new ArrayList<String>();
        populateSpinner();
        Spinner spinner = findViewById(R.id.spinner_list_title);
        spinner.setOnItemSelectedListener(new SpinnerListener());


        Button saveListNameButton = findViewById(R.id.button_save_list_title);

        saveListNameButton.setOnClickListener(this);

        //populate textbox after a item is selected from listview
        ListView listView = findViewById(R.id.list_view_items);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, (List<String>) listView);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            DBManager db = new DBManager(getApplicationContext());
            SQLiteDatabase database = db.getReadableDatabase();

            Spinner spinner = findViewById(R.id.spinner_list_title);
            String titleID = db.getTitleID(spinner.getSelectedItem().toString());

            String whereClause = DBManager.C_TITLE_ID + "=" + (titleID);
            Cursor cursor = database.query(DBManager.ITEM_TABLE_NAME, null, whereClause, null, null, null, null);

            List<String> items = new ArrayList();

            if(cursor.moveToNext())
            {
                do
                {
                    items.add(cursor.getString(2));
                } while (cursor.moveToNext());
            }
            cursor.close();
            database.close();

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditText et = findViewById(R.id.et_list_edit_item);
                et.setText(items.get(position)); //set text to selected item from list view
            }
        });
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
                EditText listItemTB = findViewById(R.id.et_list_title);
                String listItem = listItemTB.getText().toString();
                Spinner listTitle = findViewById(R.id.spinner_list_title);
                String title = listTitle.getSelectedItem().toString();
                String now = new Date().toString();

                if(listItem.trim().length() > 0)
                {
                    DBManager db = new DBManager(getApplicationContext());
                    db.insertListItem(listItem, title, now, "false");
                    listItemTB.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(listItemTB.getWindowToken(), 0);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please enter or select List Item",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.button_item_update:
            {
                EditText selectedItemTB = findViewById(R.id.et_list_edit_item);
                String selectedItem = selectedItemTB.getText().toString();

                if(selectedItem.trim().length() > 0)
                {
                    DBManager db = new DBManager(getApplicationContext());
                    db.updateItem(selectedItem);
                    selectedItemTB.setText("");
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please enter List Item",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.button_item_delete:
            {
                EditText selectedItemTB = findViewById(R.id.et_list_edit_item);
                String selectedItem = selectedItemTB.getText().toString();

                if(selectedItem.trim().length() > 0)
                {
                    DBManager db = new DBManager(getApplicationContext());
                    db.deleteItem(selectedItem);
                    selectedItemTB.setText("");
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


}
