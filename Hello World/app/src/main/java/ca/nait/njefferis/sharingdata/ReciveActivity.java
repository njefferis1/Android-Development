package ca.nait.njefferis.sharingdata;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.widget.TextView;

public class ReciveActivity extends AppCompatActivity
    {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recive);

        Bundle bundle = getIntent().getExtras();
        String prefix = bundle.getString("PREFIX");
        String data = bundle.getString("DATA");

        TextView textview = findViewById(R.id.text_view_recieved);
        textview.setText(prefix + data);
    }

}
