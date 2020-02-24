package com.example.week06;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class DBAdapter extends SimpleCursorAdapter
{
    static final String[]columns = {DBManager.C_SENDER, DBManager.C_DATE, DBManager.C_MESSAGE};
    static final int[]ids = {R.id.tv_data, R.id.tv_date, R.id.tv_message};
    public DBAdapter(Context context, Cursor cursor)
    {
        super(context, R.layout.cursor_layout, cursor, columns, ids);
    }

    //called to draw each row in the cursor
    @Override
    public void bindView(View row, Context context, Cursor cursor)
    {
        super.bindView(row, context, cursor);

        // example of overriding the default action
        String strDate = cursor.getString(cursor.getColumnIndex(DBManager.C_DATE));
        String strShortDate = strDate.substring(7, 17);
        TextView textView = row.findViewById(R.id.tv_date);
        textView.setText(strShortDate);
    }
}
