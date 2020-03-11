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
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
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

               /* database = dbManager.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.clear();
                // find way to auto increment primary key values
                values.put(DBManager.C_TITLE_ID, n);
                n++;
                values.put(DBManager.C_TITLE_DESCRIPTION, listTitle);

                try
                {
                    database.insertOrThrow(DBManager.TITLE_TABLE_NAME, null, values);
                    Log.d(TAG, "record added to database");
                }
                catch (SQLException sqle)
                {
                    Log.d(TAG, "duplicate record");
                }

                ArrayList<String> spinnerArray = new ArrayList<String>();
                populateArray(spinnerArray);
                listTitleTB.setText("");
                database.close();*/
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

            /*Spinner listTitleSpinner = findViewById(R.id.spinner_list_title);

            cursor = database.query(DBManager.TITLE_TABLE_NAME,
                    null, null, null, null, null, DBManager.C_TITLE_ID + " DESC");
            startManagingCursor(cursor);
            String title;
            while(cursor.moveToNext())
            {
                title = cursor.getString(cursor.getColumnIndex(DBManager.C_TITLE_DESCRIPTION));
                array.add(title);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, array);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listTitleSpinner.setAdapter(adapter);*/
        }
        catch(Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }


}
