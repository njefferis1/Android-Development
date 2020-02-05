package ca.nait.njefferis.oscarreviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener
{
    SharedPreferences settings;
    View mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy ourPolicy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(ourPolicy);
        }

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        settings.registerOnSharedPreferenceChangeListener(this);

        mainView = findViewById(R.id.linear_layout_main);
        String bgColor = settings.getString("preference_main_bg_color", "#000099");
        mainView.setBackgroundColor(Color.parseColor(bgColor));

        Button sendButton = findViewById(R.id.button_send_review);

        sendButton.setOnClickListener(this);
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
            case R.id.menu_view_reviews:
            {
                Intent intent = new Intent(this, ViewReviewActivity.class);
                this.startActivity(intent);
                break;
            }
        }
        return true;
    }

    private void postToServer(String category, String nominee, String review)
    {
        String loginName = settings.getString(getResources().getString(R.string.preference_user_login_name_key), "unknown");
        String password = settings.getString(getResources().getString(R.string.preference_user_password_key), "incorrectPassword");

        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpPost form = new HttpPost("http://www.youcode.ca/Lab01Servlet");
            List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
            formParameters.add(new BasicNameValuePair("REVIEW", review));
            formParameters.add(new BasicNameValuePair("REVIEWER", loginName));
            formParameters.add(new BasicNameValuePair("NOMINEE", nominee));
            formParameters.add(new BasicNameValuePair("CATEGORY", category));
            formParameters.add(new BasicNameValuePair("PASSWORD", password));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParameters);
            form.setEntity(formEntity);
            client.execute(form);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        String bgColor = settings.getString("preference_main_bg_color", "#000099");
        mainView.setBackgroundColor(Color.parseColor(bgColor));
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_send_review:
            {
                EditText nomineeTB = findViewById(R.id.nominee_textbox);
                EditText reviewTB = findViewById(R.id.review_textbox);
                RadioGroup radioG = findViewById(R.id.category_radio_group);
                int radioButtonId = radioG.getCheckedRadioButtonId();
                RadioButton radioB = findViewById(radioButtonId);
                String category = (String) radioB.getTag();
                String nominee = nomineeTB.getText().toString();
                String review = reviewTB.getText().toString();

                postToServer(category, nominee, review);
                nomineeTB.setText("");
                reviewTB.setText("");

                break;
            }
        }
    }
}
