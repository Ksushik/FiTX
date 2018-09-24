package com.brus5.lukaszkrawczak.fitx.settings.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;

import java.util.List;

/**
 * SettingsAdapter provides views for whole ListView
 * It Returns view for each object in a collection of data objects you prodive.
 * It can be used with list-based user interface widgets.
 */
public class SettingsAdapter extends ArrayAdapter<Settings>
{
    private static final String TAG = "SettingsAdapter";
    private Context context;
    private int resource;
    private MySettingsList mySettingsList;
    private String value;

    SettingsAdapter(@NonNull Context context, int resource, @NonNull List<Settings> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    public void setMySettingsList(MySettingsList mySettingsList)
    {
        this.mySettingsList = mySettingsList;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        try
        {
            final String name = getItem(position).getName();
            String value = getItem(position).getValue();
            String description = getItem(position).getDescription();
            int viewType = getItem(position).getViewType();
            final String db = getItem(position).getDb();
            final String descriptionLong = getItem(position).getDescriptionLong();

            Log.d(TAG, "getView() called with: value = [" + value + "], convertView = [" + convertView + "], parent = [" + parent + "]");

            // Creating viewType with edittext
            if (viewType == 1 || viewType == 3)
            {
                convertView = LayoutInflater.from(context).inflate(R.layout.row_settings, parent, false);

                TextView tvTitle = convertView.findViewById(R.id.textViewTitle);
                TextView tvValue = convertView.findViewById(R.id.textViewValue);
                TextView tvViewType = convertView.findViewById(R.id.textViewSettingsViewType);
                TextView tvDescription = convertView.findViewById(R.id.textViewDescription);

                tvTitle.setText(name);
                tvValue.setText(value);
                tvDescription.setText(description);
                tvViewType.setText(String.valueOf(viewType));
            }

            // Creating viewtype with switch
            if (viewType == 2)
            {
                convertView = LayoutInflater.from(context).inflate(R.layout.row_settings_switch, parent, false);

                TextView tvTitle = convertView.findViewById(R.id.textViewTitle);
                TextView tvDescription = convertView.findViewById(R.id.textViewDescription);
                final Switch aSwitch = convertView.findViewById(R.id.switch1);

                tvTitle.setText(name);
                tvDescription.setText(description);


                if (SaveSharedPreference.getAutoCalories(context) == 1)
                {
                    aSwitch.setChecked(true);
                }
                else
                {
                    aSwitch.setChecked(false);
                }

                new MySwitch(context, aSwitch, mySettingsList, db, value);


            }

        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        return convertView;
    }
}

