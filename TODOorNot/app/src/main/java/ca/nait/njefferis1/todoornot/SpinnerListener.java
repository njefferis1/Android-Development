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

public class SpinnerListener extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    ArrayList<HashMap<String, String>> todoItem = new ArrayList<>();
    public static final String ITEM_ID = "ItemId";
    public static final String ITEM = "Item";

    DBManager dbManager;
    SQLiteDatabase database;
    Cursor cursor;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //populate listView with all the list items associated with the selected List Title
       /* String[] keys = new String[]{ITEM_ID, ITEM};
        int[] ids = new int[]{R.id.custom_row_text_view_item_id, R.id.custom_row_text_view_item};

        SimpleAdapter adapter = new SimpleAdapter(this, todoItem, R.layout.custom_list_view_row, keys, ids);

        populateList();

        ListView lv = findViewById(R.id.list_view_items);

        lv.setAdapter(adapter);*/

        String label = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();
    }

    private void populateList()
    {

        try
        {
            /*Spinner listTitleSpinner = findViewById(R.id.spinner_list_title);
            ArrayList<String> array = new ArrayList<String>();

            database = dbManager.getReadableDatabase();
            String whereClause = DBManager.C_TITLE_ID + " = " + ()
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

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
