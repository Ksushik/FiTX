package com.brus5.lukaszkrawczak.fitx.settings.list;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.R;

import java.util.ArrayList;

/**
 * This class is responsible for adding Setting object to
 * ArrayList and loading whole View
 */
public class MySettingsList extends ArrayList<Settings>
{
    private Context context;
    private ListView listView;


    MySettingsList(Context context, ListView listView)
    {
        this.context = context;
        this.listView = listView;

        this.listView = ((Activity) context).findViewById(R.id.listViewSettings);


    }

    /**
     * Updating view
     */
    public void load()
    {
        // Adding new View to adapter
        SettingsAdapter adapter = new SettingsAdapter(context, R.layout.row_settings, MySettingsList.this);
        adapter.setMySettingsList(MySettingsList.this);

        // Setting adapter
        listView.setAdapter(adapter);
        listView.invalidate();
    }
}
