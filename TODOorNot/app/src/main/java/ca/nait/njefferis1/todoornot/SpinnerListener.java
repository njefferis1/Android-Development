package ca.nait.njefferis1.todoornot;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    DBManager dbManager;
    SQLiteDatabase database;
    Cursor cursor;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        //does this after being selected
        DBManager db = new DBManager(getApplicationContext());

        List<String> titles = db.getItems(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
