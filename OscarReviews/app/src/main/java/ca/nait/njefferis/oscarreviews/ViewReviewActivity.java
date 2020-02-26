package ca.nait.njefferis.oscarreviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
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

public class ViewReviewActivity extends AppCompatActivity implements View.OnClickListener
{

    ArrayList<HashMap<String, String>> reviews = new ArrayList<HashMap<String, String>>();
    public static final String DATE = "date";
    public static final String REVIEWER = "reviewer";
    public static final String CATEGORY = "category";
    public static final String NOMINEE = "nominee";
    public static final String REVIEW = "review";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_review);

        Button viewButton = findViewById(R.id.button_view_review);

        viewButton.setOnClickListener(this);
    }

    //call this method in the OnClick()
    private void displayReviews(String radioButtonSelection)
    {
        //String[] keys = new String[]{DATE, REVIEWER, CATEGORY, NOMINEE, REVIEW};
        String[] keys = new String[]{REVIEWER, CATEGORY, DATE, NOMINEE, REVIEW};
        int[] ids = new int[]{R.id.custom_row_reviewer, R.id.custom_row_category, R.id.custom_row_date, R.id.custom_row_nominee, R.id.custom_row_review};

        SimpleAdapter adapter = new SimpleAdapter(this, reviews, R.layout.custom_row_text_view, keys, ids);

        populateList(radioButtonSelection);

        ListView lv = findViewById(R.id.list_view_custom);

        lv.setAdapter(adapter);
    }

    private void populateList(String radioButtonSelection)
    {
        BufferedReader in = null;

        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.youcode.ca/Lab01Servlet?CATEGORY=" + radioButtonSelection));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";

            while((line = in.readLine()) != null)
            {
                HashMap<String, String> tempMap = new HashMap<String, String>();

                tempMap.put(DATE, line);

                line = in.readLine();
                tempMap.put(REVIEWER, line);

                line = in.readLine();
                tempMap.put(CATEGORY, line);

                line = in.readLine();
                tempMap.put(NOMINEE, line);

                line = in.readLine();
                tempMap.put(REVIEW, line);

                reviews.add(tempMap);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_view_review:
            {
                reviews.clear();

                RadioGroup radioG = findViewById(R.id.category_radio_group);
                int radioButtonId = radioG.getCheckedRadioButtonId();
                RadioButton radioB = findViewById(radioButtonId);
                String category = (String) radioB.getTag();

                displayReviews(category);



                break;
            }
        }
    }
}















