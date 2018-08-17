package com.brus5.lukaszkrawczak.fitx;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SettingsActiviy extends AppCompatActivity implements DefaultView
{
    private Configuration cfg = new Configuration();
    private ArrayList<Settings> list = new ArrayList<>();
    private ListView listView;
    private SettingsAdapter adapter;
    private Settings sett;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        cfg.changeStatusBarColor(SettingsActiviy.this, getApplicationContext(),R.id.toolbarSettingsActivity,this);
        loadInput();

        Settings s1 = new Settings();
        s1.name = "waga";
        s1.value = "100kg";
        s1.viewType = 1;

        Settings s2 = new Settings();
        s2.name = "wzrost";
        s2.value = "180cm";
        s2.viewType = 2;

        list.add(s1);
        list.add(s2);



        adapter = new SettingsAdapter(this,R.layout.row_settings,list);
        listView.setAdapter(adapter);
        listView.invalidate();
    }

    @Override
    public void loadInput()
    {
        listView = findViewById(R.id.listViewSettings);
    }


    class Settings
    {
        String name;
        String value;
        int viewType;

        public String getName()
        {
            return name;
        }

        public String getValue()
        {
            return value;
        }

        public int getViewType()
        {
            return viewType;
        }
    }

    class SettingsAdapter extends ArrayAdapter<Settings>
    {
        private Context mContext;
        private int mResource;

        public SettingsAdapter(@NonNull Context context, int resource, @NonNull List<Settings> objects)
        {
            super(context, resource, objects);
            this.mContext = context;
            this.mResource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            String name = getItem(position).getName();
            String value = getItem(position).getValue();
            int viewType = getItem(position).getViewType();

            TextView tvTitle = convertView.findViewById(R.id.searchTitle);
            TextView tvValue = convertView.findViewById(R.id.textViewSettingsValue);

            tvTitle.setText(name);
            tvValue.setText(value);

            return convertView;
        }
    }

}
