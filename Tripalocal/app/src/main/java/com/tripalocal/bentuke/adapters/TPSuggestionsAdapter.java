package com.tripalocal.bentuke.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tripalocal.bentuke.R;

/**
 * Created by naveen on 6/8/2015.
 */
public class TPSuggestionsAdapter extends CursorAdapter {

    private String[] data;
    private TextView text_city,text_state;

    public TPSuggestionsAdapter(Context context, Cursor c, String[] data_list){
        super(context, c, false);
        this.data = data_list;
    }

    public TPSuggestionsAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.search_item, viewGroup, false);

        text_city = (TextView) view.findViewById(R.id.search_city);
        text_state=(TextView)view.findViewById(R.id.search_state);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        text_city.setText(data[(cursor.getPosition())].split(",")[0]);
        text_state.setText(data[(cursor.getPosition())].split(",")[1]);

    }
}
