package ca.nait.gschenk.chatter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class ColorSpinnerActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_spinner);

        Spinner spinner = findViewById(R.id.spinner_color_changer);

        ////////////////////////////////////////////////
        // use an explicit instantiation of the listener to register it
        MyColorSpinnerListener listener = new MyColorSpinnerListener();
        spinner.setOnItemSelectedListener(listener);
        // both do the same thing
        ////////////////////////////////////////////////
        // use an implicit instantiation of the listener
        spinner.setOnItemSelectedListener(new MyColorSpinnerListener());

    }
}

//custom class we coded
class MyColorSpinnerListener implements AdapterView.OnItemSelectedListener
{

    @Override
    public void onItemSelected(AdapterView<?> spinner, View row, int position, long id)
    {
        View linearLayout = (View)spinner.getParent();
        String bgColor = spinner.getResources().getStringArray(R.array.color_values)[position];
        linearLayout.setBackgroundColor(Color.parseColor(bgColor));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        // used for an empty list
    }
}
