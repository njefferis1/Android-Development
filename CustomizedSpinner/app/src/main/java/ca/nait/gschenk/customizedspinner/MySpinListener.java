package ca.nait.gschenk.customizedspinner;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;

public class MySpinListener implements OnItemSelectedListener
{
    static MainActivity activity;

    public MySpinListener(Context context)
    {
        activity = (MainActivity)context;

    }

    @Override
    public void onItemSelected(AdapterView<?> spinner, View row, int position, long id)
    {
        Chat chat = (Chat)spinner.getAdapter().getItem(position);

        EditText et_sender = activity.findViewById(R.id.et_sender);
        EditText et_date = activity.findViewById(R.id.et_date);
        EditText et_content = activity.findViewById(R.id.et_content);

        et_sender.setText(chat.getChatSender());
        et_date.setText(chat.getChatDate());
        et_content.setText(chat.getChatContent());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        // called when list is empty
    }
}
