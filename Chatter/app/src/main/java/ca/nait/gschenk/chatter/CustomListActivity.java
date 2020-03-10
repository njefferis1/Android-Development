package ca.nait.gschenk.chatter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

public class CustomListActivity extends AppCompatActivity
{
    ArrayList<HashMap<String, String>> chatter = new ArrayList<HashMap<String, String>>();
    public static final String SENDER = "sender";
    public static final String TEXT = "text";
    public static final String DATE = "myDate";
    //to use constants in other classes use CustomListActivity.CONSTANT_NAME


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);
        displayChatter();
    }

    private void displayChatter()
    {
        String[] keys = new String[]{SENDER, TEXT, DATE};
        int[] ids = new int[]{R.id.custom_row_text_view_sender, R.id.custom_row_text_view_message, R.id.custom_row_text_view_date};

        SimpleAdapter adapter = new SimpleAdapter(this, chatter, R.layout.list_view_custom_row, keys, ids);

        populateList();

        ListView lv = findViewById(R.id.list_view_custom);

        lv.setAdapter(adapter);
    }

    private void populateList()
    {
        BufferedReader in = null;

        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.youcode.ca/JitterServlet"));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";

            while((line = in.readLine()) != null)
            {
                HashMap<String, String> tempMap = new HashMap<String, String>();

                tempMap.put(SENDER, line);

                line = in.readLine();
                tempMap.put(TEXT, line);

                line = in.readLine();
                tempMap.put(DATE, line);

                chatter.add(tempMap);
            }
            in.close();
        }
        catch(Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }
}
