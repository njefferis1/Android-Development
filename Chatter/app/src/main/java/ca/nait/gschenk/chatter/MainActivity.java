package ca.nait.gschenk.chatter;

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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener
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
        String bgColor = settings.getString("preference_main_bg_color", "#009999");
        mainView.setBackgroundColor(Color.parseColor(bgColor));
        //^^comment out this line if color is set to invalid value

        Button sendButton = findViewById(R.id.button_send_data);
        Button viewButton = findViewById(R.id.button_view_chatter);

        sendButton.setOnClickListener(this);
        viewButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId())
        {
            case (R.id.menu_item_view_text_view):
            {
                Intent intent = new Intent(this, ReceiveActivity.class);
                this.startActivity(intent);
                break;
            }
            case (R.id.menu_item_view_system_list):
            {
                Intent intent = new Intent(this, SystemListActivity.class);
                this.startActivity(intent);
                break;
            }
            case (R.id.menu_item_view_custom_list):
            {
                Intent intent = new Intent(this, CustomListActivity.class);
                this.startActivity(intent);
                break;
            }
            case (R.id.menu_item_preferences):
            {
                Intent intent = new Intent(this, SettingsActivity.class);
                this.startActivity(intent);
                break;
            }
        }
        return true;
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.button_send_data:
            {
                EditText textBox = findViewById(R.id.textbox_data);
                String msg = textBox.getText().toString();
                postToServer(msg);
                textBox.setText("");

                break;
            }
            case R.id.button_view_chatter:
            {
                Intent intent = new Intent(this, ReceiveActivity.class);
                startActivity(intent);

                break;
            }
        }

    }

    private void postToServer(String msg)
    {
        // key value in preferences xml and alternate value "unknown"
        String userName = settings.getString("preference_user_name", "unknown");

        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpPost form = new HttpPost("http://www.youcode.ca/JitterServlet");
            List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
            formParameters.add(new BasicNameValuePair("DATA", msg));
            formParameters.add(new BasicNameValuePair("LOGIN_NAME", userName));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParameters);
            form.setEntity(formEntity);
            client.execute(form);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }

    // changes background color when preference changed
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        String bgColor = settings.getString("preference_main_bg_color", "#009999");
        mainView.setBackgroundColor(Color.parseColor(bgColor));
    }
}










