package com.brus5.lukaszkrawczak.fitx.async.inflater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.MainRow;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.SettingsActivity;
import com.brus5.lukaszkrawczak.fitx.SettingsDetailsActivity;
import com.brus5.lukaszkrawczak.fitx.training.TrainingInflater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SuppressLint("LongLogTag")
public class SettingsActivityInflater
{
    private static final String TAG = "SettingsActivityInflater";

    private Context context;
    private ListView listView;
    private MainRow mainRow;
    private MainAdapter adapter;
    private ArrayList<MainRow> list = new ArrayList<>();

    public SettingsActivityInflater(Context context, ListView listView, String response)
    {
        this.context = context;
        this.listView = listView;

        dataInflater(response);
    }


    private void dataInflater(String s)
    {
        Log.d(TAG, "dataInflater() called with: s = [" + s + "]");
    }

    private class Settings
    {
        String name;
        String value;
        String description;
        String db;
        int viewType;

        public String getName()
        {
            return name;
        }

        public String getValue()
        {
            return value;
        }

        public String getDescription() {
            return description;
        }

        public int getViewType()
        {
            return viewType;
        }

        public String getDb()
        {
            return db;
        }
    }

    private class SettingsAdapter extends ArrayAdapter<Settings>
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
            String description = getItem(position).getDescription();
            int viewType = getItem(position).getViewType();
            final String db = getItem(position).getDb();

            if (viewType == 1)
            {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_settings, parent, false);

                TextView tvTitle = convertView.findViewById(R.id.textViewTitle);
                TextView tvValue = convertView.findViewById(R.id.textViewValue);
                TextView tvViewType = convertView.findViewById(R.id.textViewSettingsViewType);
                TextView tvDescription = convertView.findViewById(R.id.textViewDescription);

                tvTitle.setText(name);
                tvValue.setText(value);
                tvDescription.setText(description);
                tvViewType.setText(String.valueOf(viewType));

                System.out.println("DB: " + db);


                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        System.out.println("CLICKED");
                        Intent intent = new Intent(context, SettingsDetailsActivity.class);
                        intent.putExtra("test","testuje sobie");
                        intent.putExtra("db",db);
                        startActivity(intent);
                    }
                });

            }

            if (viewType == 2)
            {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_settings_switch, parent, false);

                TextView tvTitle = convertView.findViewById(R.id.textViewTitle);
                TextView tvDescription = convertView.findViewById(R.id.textViewDescription);
                Switch aSwitch = convertView.findViewById(R.id.switch1);
                TextView tvViewType = convertView.findViewById(R.id.textViewSettingsViewType);

                tvTitle.setText(name);
                tvDescription.setText(description);
                tvViewType.setText(String.valueOf(viewType));

                aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
                    {
                        if (b)
                        {
                            Toast.makeText(mContext, "ON", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(mContext, "OFF", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            return convertView;
        }
    }


}