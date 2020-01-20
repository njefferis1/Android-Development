package ca.nait.gschenk.chatter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
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

public class CustomListActivity extends ListActivity
{
    ArrayList<HashMap<String, String>> chatter = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);
        displayChatter();
    }

    private void displayChatter()
    {
        String[] keys = new String[]{"sender", "text", "theDate"};
        int[] ids = new int[]{R.id.custom_row_text_view_sender, R.id.custom_row_text_view_message, R.id.custom_row_text_view_date};

        SimpleAdapter adapter = new SimpleAdapter(this, chatter, R.layout.list_view_custom_row, keys, ids);

        populateList();

        setListAdapter(adapter);
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

                tempMap.put("sender", line);

                line = in.readLine();
                tempMap.put("text", line);

                line = in.readLine();
                tempMap.put("theDate", line);

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
