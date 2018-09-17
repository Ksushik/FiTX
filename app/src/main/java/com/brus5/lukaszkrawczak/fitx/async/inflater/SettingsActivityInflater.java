package com.brus5.lukaszkrawczak.fitx.async.inflater;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import com.brus5.lukaszkrawczak.fitx.MainService;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.SettingsDetailsActivity;
import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_SETTINGS_SET_AUTO_CALORIES;

@SuppressLint("LongLogTag")
public class SettingsActivityInflater
{
    private static final String TAG = "SettingsActivityInflater";

    private Context context;
    private ListView listView;
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
            String auto_calories = array.getJSONObject(3).getString("auto_calories");
            String calories_limit = array.getJSONObject(4).getString("calories_limit");

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

            String b = "+ " + somatotype + " kcal";

            String st6 = "Somatotyp, to typ budowy ciała, określa całościowe proporcje ciała. Jako regułę wyróżnia się trzy somatotypy - ektomorfik, mezomorfik i endomorfik. Każdy z nich charakteryzuje się inną budową. Wybranie jednej z tych grup pozwoli na trafniejsze wyliczanie kalorii a co za tym idzie lepsze efekty widoczne na ciele.\n" +
                    "\n" +
                    "+ 900 kcal - jeżeli masz niski poziom tkanki tłuszczowej, mało mięśni, dość szczupłe ramiona, płaską klatkę piersiową.\n" +
                    "\n" +
                    "+ 400 kcal - jeżeli masz niski poziom tkanki tłuszczowej, szerokie barki, wąską talię, widoczne mięśnie.\n" +
                    "\n" +
                    "+ 200 kcal - jeżeli masz wysoki poziom tkanki tłuszczowej, łatwo przybierasz na masie mięśniowej oraz tłuszczowej.\n" +
                    "\n" +
                    "Nie jesteśmy podzieleni tylko na trzy kategorie. Jeżeli wydaje Ci się, że przynależysz do jednej jak i do drugiej grupy to możesz wpisać wartość pośrednią. \n" +
                    "Przykład: Możesz posiadać cechy endomorficzne jak i mezomorficzne w swoim somatotypie. Wtedy sugerowana wartość pośrednia wynosiłaby +300 kcal. Również możesz posiadać cechy ektomorficzne jak i mezomorficzne to Twoja wartość wynosiłaby między 400 a 900 kcal.";

            Settings mSomatotype = new Settings(st1, b, st3, st4, defaultView, st6);

            arrayList.add(mSomatotype);


            // Creating variables for passing AUTOMATIC CALORIES constructor
            int switchView = 2;
            String ac1 = context.getResources().getString(R.string.calories_automatic);
            String ac3 = context.getResources().getString(R.string.calories_automatic_long);
            String ac4 = "user_calories_limit_auto";


            Settings mLimit = new Settings(ac1,auto_calories,ac3,ac4,switchView,st6);
            arrayList.add(mLimit);


            // If Automatic calories are turned OFF then be able to add manual calories row
            if (Integer.valueOf(auto_calories) == 0)
            {
                String mc1 = context.getResources().getString(R.string.calories_manual);
                String mc3 = context.getResources().getString(R.string.calories_manual_long);
                String mc4 = "user_calories_limit";
                String mc6 = "Long text";
                Settings mManual = new Settings(mc1, calories_limit,mc3,mc4,defaultView,mc6);
                arrayList.add(mManual);

                SaveSharedPreference.setAutoCalories(context,0);
                Log.d(TAG, "dataInflater() called with: SaveSharedPreference.getAutoCalories(context) = [" + SaveSharedPreference.getAutoCalories(context) + "]");
            }

            // Adding new View to adapter
            SettingsAdapter adapter = new SettingsAdapter(context, R.layout.row_settings, arrayList);

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

        SettingsAdapter(@NonNull Context context, int resource, @NonNull List<Settings> objects)
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

            try {
                final String name = getItem(position).getName();
                String value = getItem(position).getValue();
                String description = getItem(position).getDescription();
                int viewType = getItem(position).getViewType();
                final String db = getItem(position).getDb();
                final String descriptionLong = getItem(position).getDescriptionLong();

                // Creating viewType with edittext
                if (viewType == 1) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.row_settings, parent, false);

                    TextView tvTitle = convertView.findViewById(R.id.textViewTitle);
                    TextView tvValue = convertView.findViewById(R.id.textViewValue);
                    TextView tvViewType = convertView.findViewById(R.id.textViewSettingsViewType);
                    TextView tvDescription = convertView.findViewById(R.id.textViewDescription);

                    tvTitle.setText(name);
                    tvValue.setText(value);
                    tvDescription.setText(description);
                    tvViewType.setText(String.valueOf(viewType));

                    convertView.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            Intent intent = new Intent(context, SettingsDetailsActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("descriptionLong", descriptionLong);
                            intent.putExtra("db", db);
                            context.startActivity(intent);
                        }
                    });

                }

                // Creating viewtype with switch
                if (viewType == 2) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.row_settings_switch, parent, false);

                    TextView tvTitle = convertView.findViewById(R.id.textViewTitle);
                    TextView tvDescription = convertView.findViewById(R.id.textViewDescription);
                    Switch aSwitch = convertView.findViewById(R.id.switch1);

                    tvTitle.setText(name);
                    tvDescription.setText(description);

                    if (Integer.valueOf(value) == 1) {
                        aSwitch.setChecked(true);
                        SaveSharedPreference.setAutoCalories(context, 1);
                    } else {
                        aSwitch.setChecked(false);
                        SaveSharedPreference.setAutoCalories(context, 0);
                    }


                    aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b)
                        {
                            String id = String.valueOf(SaveSharedPreference.getUserID(context));

                            switch (db) {
                                case "user_calories_limit_auto":
                                    String auto_calories = b ? "1" : "0";
                                    final String LINK = URL_SETTINGS_SET_AUTO_CALORIES + "?id=" + id + "&auto_calories=" + auto_calories;

                                    // Set Automatic Calories
                                    new MainService(context).post(LINK);

                                    ((Activity) context).finish();
                                    ((Activity) context).overridePendingTransition(0, 0);
                                    context.startActivity(((Activity) context).getIntent());
                                    ((Activity) context).overridePendingTransition(0, 0);
                                    break;
                            }


                            if (b) {
                                Toast.makeText(mContext, db + " ON", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, db + " OFF", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return convertView;
        }

    }


}

class Settings
{
    private String name;
    private String value;
    private String description;
    private String db;
    private int viewType;
    private String descriptionLong;

    Settings(String name, String value, String description, String db, int viewType, String descriptionLong)
    {
        this.name = name;
        this.value = value;
        this.description = description;
        this.db = db;
        this.viewType = viewType;
        this.descriptionLong = descriptionLong;

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