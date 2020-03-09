package ca.nait.njefferis1.todoornot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    static final String TAG = "MainActivity";

    DBManager dbManager;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button saveListNameButton = findViewById(R.id.button_save_list_title);

        saveListNameButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int n = 1;
        switch (v.getId())
        {
            case R.id.button_save_list_title:
            {
                EditText listTitleTB = findViewById(R.id.et_list_title);
                String listTitle = listTitleTB.getText().toString();

                database = dbManager.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.clear();

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
                database.close();


                listTitleTB.setText("");
                break;
            }
        }
    }
}
