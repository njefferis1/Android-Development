package ca.nait.njefferis.sharingdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// added implement OnClickListener
public class MainActivity extends AppCompatActivity implements OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hooks up the button to main activity
        Button sendButton = findViewById(R.id.button_send_data);
        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        EditText editText = findViewById(R.id.edit_text_data);
        String data = editText.getText().toString();

        // pop up
        //Toast.makeText(this, "you entered: " + data, Toast.LENGTH_LONG).show();

        //create message
        Intent intent = new Intent(this, ReciveActivity.class);
        //create data
        Bundle bundle = new Bundle();
        bundle.putString("PREFIX", "From main");
        bundle.putString("DATA", data);
        //add data to message
        intent.putExtras(bundle);
        //start activity with message
        startActivity(intent);
    }
}
