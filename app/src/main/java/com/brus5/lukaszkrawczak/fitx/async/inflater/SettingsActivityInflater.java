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

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.SettingsDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("LongLogTag")
public class SettingsActivityInflater
{
    private static final String TAG = "SettingsActivityInflater";

    private Context context;
    private ListView listView;

    private SettingsAdapter adapter;
    private ArrayList<Settings> arrayList = new ArrayList<>();

    public SettingsActivityInflater(Context context, ListView listView, String response)
    {
        this.context = context;
        this.listView = listView;

        dataInflater(response);
    }


    private void dataInflater(String s)
    {
        Log.d(TAG, "dataInflater() called with: s = [" + s + "]");

        try
        {
            // Creating JSON Object with value fetched from "s" paramtere
            JSONObject json = new JSONObject(s);

            // Getting array from JSONObject named "server_response"
            JSONArray array = json.getJSONArray("server_response");

            // Getting JSONObject of array with index and then getting specific String name
            String weight = array.getJSONObject(0).getString("weight");
            String height = array.getJSONObject(1).getString("height");
            String somatotype = array.getJSONObject(2).getString("somatotype");

            // Creating ROW View Type
            int defaultView = 1;

            // Creating variables for passing WEIGHT constructor
            String s1 = context.getResources().getString(R.string.body_mass);
            String s3 = context.getResources().getString(R.string.body_mass_descripition_short);
            String s4 = "user_weight";
            String s6 = context.getResources().getString(R.string.body_mass_descripition_long);

            Settings mWeight = new Settings(s1,weight,s3,s4,defaultView,s6);

            arrayList.add(mWeight);

            // Creating variables for passing HEIGHT constructor
            String h1 = context.getResources().getString(R.string.body_height);
            String h3 = context.getResources().getString(R.string.body_height_description_short);
            String h4 = "user_height";
            String h6 = context.getResources().getString(R.string.body_height_description_long);
            Settings mHeight = new Settings(h1,height,h3,h4,defaultView,h6);

            arrayList.add(mHeight);


            // Creating variables for passing SOMATOTYPE constructor
            String st1 = context.getResources().getString(R.string.somatotype);
            String st3 = context.getResources().getString(R.string.your_body_type);
            String st4 = "user_somatotype";

            StringBuilder b = new StringBuilder();
            b.append("+");
            b.append(somatotype);
            b.append(" kcal");

            String st6 = "?";

            Settings mSomatotype = new Settings(st1,b.toString(),st3,st4,defaultView,st6);

            arrayList.add(mSomatotype);





            // Adding new View to adapter
            adapter = new SettingsAdapter(context,R.layout.row_settings,arrayList);

            // Setting adapter
            listView.setAdapter(adapter);


        }
        catch(JSONException e)
        {
            Log.e(TAG, "dataInflater: ",e );
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

            final String name = getItem(position).getName();
            String value = getItem(position).getValue();
            String description = getItem(position).getDescription();
            int viewType = getItem(position).getViewType();
            final String db = getItem(position).getDb();
            final String descriptionLong = getItem(position).getDescriptionLong();

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

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(context, SettingsDetailsActivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("descriptionLong",descriptionLong);
                        intent.putExtra("db",db);
                        context.startActivity(intent);
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

class Settings
{
    String name;
    String value;
    String description;
    String db;
    int viewType;
    String descriptionLong;

    public Settings(String name, String value, String description, String db, int viewType, String descriptionLong)
    {
        this.name = name;
        this.value = value;
        this.description = description;
        this.db = db;
        this.viewType = viewType;
        this.descriptionLong = descriptionLong;

    }

    public Settings(Builder builder)
    {
        this.name = builder.name;
        this.value = builder.value;
        this.description = builder.description;
        this.db = builder.db;
        this.viewType = builder.viewType;
    }

    public static class Builder
    {
        String name;
        String value;
        String description;
        String db;
        int viewType;

        public Builder name(String name)
        {
            this.name = name;
            return this;
        }

        public Builder value(String value)
        {
            this.value = value;
            return this;
        }

        public Builder description(String description)
        {
            this.description = description;
            return this;
        }

        public Builder db(String db)
        {
            this.db = db;
            return this;
        }

        public Builder viewType(int number)
        {
            this.viewType = viewType;
            return this;
        }



        public Settings build()
        {
            return new Settings(this);
        }
    }

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

    public String getDescriptionLong()
    {
        return descriptionLong;
    }
}