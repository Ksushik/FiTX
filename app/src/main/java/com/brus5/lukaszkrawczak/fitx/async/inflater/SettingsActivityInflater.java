package com.brus5.lukaszkrawczak.fitx.async.inflater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.MainService;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.settings.SettingsDetailsActivity;
import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_SETTINGS_SET_AUTO_CALORIES;

@SuppressLint ("LongLogTag")
public class SettingsActivityInflater
{
    private static final String TAG = "SettingsActivityInflater";
    private Context context;
    private ListView listView;
    private String calories_limit;
    private MySettingsList mySettingsList = new MySettingsList();

    public SettingsActivityInflater(Context context, String response)
    {
        this.context = context;
        listView = ((Activity) context).findViewById(R.id.listViewSettings);
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
            calories_limit = array.getJSONObject(4).getString("calories_limit");
            String goal = "123";






            // Creating ROW View Type
            int defaultView = 1;


            new DefaultTypeView(weight);



            // Creating variables for passing WEIGHT constructor
//            String s1 = context.getResources().getString(R.string.body_mass);
//            String s3 = context.getResources().getString(R.string.body_mass_descripition_short);
//            String s4 = "user_weight";
//            String s6 = context.getResources().getString(R.string.body_mass_descripition_long);
//
//            Settings mWeight = new Settings(s1, weight, s3, s4, defaultView, s6);
//
//            mySettingsList.add(mWeight);
//            mySettingsList.setData();





            // Creating variables for passing HEIGHT constructor
            String h1 = context.getResources().getString(R.string.body_height);
            String h3 = context.getResources().getString(R.string.body_height_description_short);
            String h4 = "user_height";
            String h6 = context.getResources().getString(R.string.body_height_description_long);

            Settings mHeight = new Settings(h1, height, h3, h4, defaultView, h6);
            mySettingsList.add(mHeight);
            mySettingsList.load();





            // Creating variables for passing SOMATOTYPE constructor
            String st1 = context.getResources().getString(R.string.somatotype);
            String st3 = context.getResources().getString(R.string.your_body_type);
            String st4 = "user_somatotype";

            String b = "+ " + somatotype + " kcal";

            String st6 = "Somatotyp, to typ budowy ciała, określa całościowe proporcje ciała. Jako regułę wyróżnia się trzy somatotypy - ektomorfik, mezomorfik i endomorfik. Każdy z nich charakteryzuje się inną budową. Wybranie jednej z tych grup pozwoli na trafniejsze wyliczanie kalorii.\n" +
                    "\n" +
                    "+ 900 kcal - jeżeli masz niski poziom tkanki tłuszczowej, mało mięśni, dość szczupłe ramiona, płaską klatkę piersiową.\n" +
                    "\n" +
                    "+ 400 kcal - jeżeli masz niski poziom tkanki tłuszczowej, szerokie barki, wąską talię, widoczne mięśnie.\n" +
                    "\n" +
                    "+ 200 kcal - jeżeli masz wysoki poziom tkanki tłuszczowej, łatwo przybierasz na masie mięśniowej oraz tłuszczowej.\n" +
                    "\n" +
                    "Nie jesteśmy podzieleni tylko na trzy kategorie. Jeżeli wydaje Ci się, że przynależysz do jednej jak i do drugiej grupy to możesz wpisać wartość pośrednią. \n" +
                    "Przykład: Możesz posiadać cechy endomorficzne jak i mezomorficzne. Wtedy sugerowana wartość pośrednia wynosiłaby +300 kcal. Również możesz posiadać cechy ektomorficzne jak i mezomorficzne to Twoja wartość wynosiłaby między 400 a 900 kcal.";

            Settings mSomatotype = new Settings(st1, b, st3, st4, defaultView, st6);
            mySettingsList.add(mSomatotype);
            mySettingsList.load();





            // Creating variables for passing AUTOMATIC CALORIES constructor
            int switchView = 2;
            String ac1 = context.getResources().getString(R.string.calories_automatic);
            String ac3 = context.getResources().getString(R.string.calories_automatic_long);
            String ac4 = "user_calories_limit_auto";

            Settings mLimit = new Settings(ac1, auto_calories, ac3, ac4, switchView, st6);
            mySettingsList.add(mLimit);
            mySettingsList.load();

            Log.i(TAG, "auto_calories: " + auto_calories);

            // If Automatic calories are turned OFF then be able to add manual calories row
            if (SaveSharedPreference.getAutoCalories(context) == 0)
            {

                String mc1 = context.getResources().getString(R.string.calories_manual);
                String mc3 = context.getResources().getString(R.string.calories_manual_long);
                String mc4 = "user_calories_limit";
                String mc6 = "Long text";
                Settings mManual = new Settings(mc1, calories_limit, mc3, mc4, defaultView, mc6);
                mySettingsList.add(mManual);
                mySettingsList.load();

                Log.d(TAG, "dataInflater() called with: SaveSharedPreference.getAutoCalories(context) = [" + SaveSharedPreference.getAutoCalories(context) + "]");
            }


            // Creating variables for Activate Triple option chooser
            int tripleChoose = 3;
            String tc1 = context.getResources().getString(R.string.settings_user_goal);
            String tc3 = context.getResources().getString(R.string.settings_user_goal_description_short);
            String tc4 = "user_goals";

            Settings myGoals = new Settings(tc1, goal, tc3, tc4, tripleChoose, null);
            mySettingsList.add(myGoals);
            mySettingsList.load();

            new OnItemClicked();


        } catch (JSONException e)
        {
            Log.e(TAG, "dataInflater: ", e);
        }
    }

    /**
     * SettingsAdapter provides views for whole ListView
     * It Returns view for each object in a collection of data objects you prodive.
     * It can be used with list-based user interface widgets.
     */
    class SettingsAdapter extends ArrayAdapter<Settings>
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

            try
            {
                final String name = getItem(position).getName();
                String value = getItem(position).getValue();
                String description = getItem(position).getDescription();
                int viewType = getItem(position).getViewType();
                final String db = getItem(position).getDb();
                final String descriptionLong = getItem(position).getDescriptionLong();

                // Creating viewType with edittext
                if (viewType == 1 || viewType == 3)
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
                }

                // Creating viewtype with switch
                if (viewType == 2)
                {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.row_settings_switch, parent, false);

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


                    aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b)
                        {
                            String id = String.valueOf(SaveSharedPreference.getUserID(context));

                            switch (db)
                            {
                                case "user_calories_limit_auto":
                                {
                                    String auto_calories = b ? "1" : "0";

                                    if (b) SaveSharedPreference.setAutoCalories(context, 1);
                                    else SaveSharedPreference.setAutoCalories(context, 0);

                                    Toast.makeText(mContext, String.valueOf(b), Toast.LENGTH_SHORT).show();

                                    if (!b)
                                    {
                                        String mc1 = context.getResources().getString(R.string.calories_manual);
                                        String mc3 = context.getResources().getString(R.string.calories_manual_long);
                                        String mc4 = "user_calories_limit";
                                        String mc6 = "Long text";
                                        Settings mManual = new Settings(mc1, calories_limit, mc3, mc4, 1, mc6);

                                        mySettingsList.add(4, mManual);
                                        mySettingsList.load();
                                    }
                                    else
                                    {
                                        mySettingsList.remove(4);
                                        mySettingsList.load();
                                    }

                                    final String link = URL_SETTINGS_SET_AUTO_CALORIES + "?id=" + id + "&auto_calories=" + auto_calories;
                                    // Set Automatic Calories
                                    new MainService(context).post(link);
                                }
                            }

                            if (b)
                            {
                                Toast.makeText(mContext, db + " ON", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(mContext, db + " OFF", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }

            return convertView;
        }
    }

    /**
     * This class is responsible for creating Triple Choose AlertDialog
     * one selected option.
     */
    private class TripleAlertDialog
    {
        private final String[] items = new String[]{"Zwiększanie masy", "Balans", "Redukcja"};
        private String goal;

        TripleAlertDialog(Settings itemClicked)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(context)
                    .setTitle(itemClicked.getName())
                    .setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Toast.makeText(context, "which: " + which, Toast.LENGTH_SHORT).show();
                            if (which == 0) setGoal(items[0]);
                            if (which == 1) setGoal(items[1]);
                            if (which == 3) setGoal(items[2]);
                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Toast.makeText(context, "pos: " + getGoal() + " accepted. Send data to DB", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .create();
            alertDialog.show();
        }

        private String getGoal()
        {
            if (goal == null || goal.equals("null")) return items[1];
            else return goal;
        }

        private void setGoal(String goal)
        {
            this.goal = goal;
        }
    }

    /**
     * Settings object, for creating objects in ListView
     */
    private class Settings
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

        public String getDescription()
        {
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


    /**
     * This class is responsible for adding Setting object to
     * ArrayList and loading whole View
     */
    private class MySettingsList extends ArrayList<Settings>
    {
        MySettingsList() {}

        /**
         * Updating view
         */
        void load()
        {
            // Adding new View to adapter
            SettingsAdapter adapter = new SettingsAdapter(context, R.layout.row_settings, mySettingsList);

            // Setting adapter
            listView.setAdapter(adapter);
        }

        @Override
        public boolean add(Settings settings)
        {
            return super.add(settings);
        }
    }


    /**
     * What happens when you click on specific item of ListView
     */
    private class OnItemClicked
    {
        OnItemClicked()
        {
            ListView listView = ((Activity) context).findViewById(R.id.listViewSettings);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    // Getting position of clicked item
                    Settings itemClicked = mySettingsList.get(position);


                    String name = itemClicked.getName();
                    String descriptionLong = itemClicked.getDescriptionLong();
                    String db = itemClicked.getDb();
                    int viewType = itemClicked.getViewType();

                    // If viewType is standard view with value
                    if (viewType == 1)
                    {
                        Intent intent = new Intent(context, SettingsDetailsActivity.class);
                        intent.putExtra("name", name);
                        intent.putExtra("descriptionLong", descriptionLong);
                        intent.putExtra("db", db);
                        context.startActivity(intent);
                    }

                    // If viewType is with switch
                    if (viewType == 2)
                    {
                        Toast.makeText(context, "name: " + itemClicked.getName() + " pos: " + position, Toast.LENGTH_SHORT).show();
                    }

                    // If view Type is with Triple Chooser
                    if (viewType == 3)
                    {
                        Toast.makeText(context, "name: " + itemClicked.getName() + " pos: " + position, Toast.LENGTH_SHORT).show();

                        new TripleAlertDialog(itemClicked);
                    }
                }
            });
        }
    }

    private abstract class MyRow
    {
        MySettingsList mySettingsList = new MySettingsList();
        private String name;
        private String value;
        private String descriptionShort;
        private String dbName;
        private int viewType;
        private String descriptionLong;

        MyRow()
        {

        }


        public boolean setData()
        {
            Settings settings = new Settings(name, value, descriptionShort, dbName, viewType, descriptionLong);

            return mySettingsList.add(settings);
//            mySettingsList.add(settings);
        }
    }


    private class DefaultTypeView extends MyRow
    {
        private String name = context.getResources().getString(R.string.body_mass);
        private String descriptionShort = context.getResources().getString(R.string.body_mass_descripition_short);
        private String dbName = "user_weight";
        private String descriptionLong = context.getResources().getString(R.string.body_mass_descripition_long);

        DefaultTypeView(String value)
        {
            super.name = name;
            super.value = value;
            super.descriptionShort = descriptionShort;
            super.dbName = dbName;
            super.viewType = 1;
            super.descriptionLong = descriptionLong;
            setData();

            Log.d(TAG, "DefaultTypeView() called with: name = [" + name + "]");
            Log.d(TAG, "DefaultTypeView() called with: descriptionShort = [" + descriptionShort + "]");
            Log.d(TAG, "DefaultTypeView() called with: dbName = [" + dbName + "]");
            Log.d(TAG, "DefaultTypeView() called with: descriptionLong = [" + descriptionLong + "]");
        }
    }


}

