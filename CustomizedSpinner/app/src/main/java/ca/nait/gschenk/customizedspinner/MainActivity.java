package ca.nait.gschenk.customizedspinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ArrayList array = new ArrayList();
        populateArray(array);
        MySpinAdapter adapter = new MySpinAdapter(this, 1, array);
        Spinner spinner = findViewById(R.id.spinner_chatter);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new MySpinListener(this));
    }

    private void populateArray(ArrayList array)
    {
        BufferedReader in = null;
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            String url = "http://www.youcode.ca/JitterServlet";
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity()
                    .getContent()));
            String line = "";

            while ((line = in.readLine()) != null)
            {
                Chat temp = new Chat();

                temp.setChatSender(line);

                line = in.readLine();
                temp.setChatContent(line);

                line = in.readLine();
                temp.setChatDate(line);

                array.add(temp);
            }
            in.close();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }

    }
}
