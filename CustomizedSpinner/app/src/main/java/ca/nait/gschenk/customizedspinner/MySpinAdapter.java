package ca.nait.gschenk.customizedspinner;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



import java.util.ArrayList;

public class MySpinAdapter extends ArrayAdapter
{
    private Context context;
    private ArrayList array;



    public MySpinAdapter(Context context, int textViewResourceId, ArrayList objects)
    {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.array = objects;
    }

    @Override
    public int getCount()
    {
        return array.size();
    }

    @Override
    public Object getItem(int position)
    {
        return array.get(position);
    }
    // called to draw the closed spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        TextView label = new TextView(context);

        label.setTextColor(Color.MAGENTA);

        Chat message = (Chat)array.get(position);
        label.setTextSize(20);
        label.setText(message.getChatContent());

        return label;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        MainActivity activity = (MainActivity)context;
        LayoutInflater inflater = activity.getLayoutInflater();
        View spinnerRow = inflater.inflate(R.layout.spinner_row, null);

        TextView tv_sender = spinnerRow.findViewById(R.id.textview_chat_sender);
        TextView tv_date = spinnerRow.findViewById(R.id.textview_chat_date);
        TextView tv_content = spinnerRow.findViewById(R.id.textview_chat_content);

        Chat chat = (Chat)array.get(position);

        tv_sender.setText(chat.getChatSender());
        tv_date.setText(chat.getChatDate());
        tv_content.setText(chat.getChatContent());

        return spinnerRow;
    }
}
