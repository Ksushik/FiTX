package com.brus5.lukaszkrawczak.fitx.Diet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.Async.Provider.Provider;
import com.brus5.lukaszkrawczak.fitx.DefaultView;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.Utils.ActivityView;
import com.brus5.lukaszkrawczak.fitx.Utils.DateGenerator;
import com.brus5.lukaszkrawczak.fitx.Utils.MyCalendar;

public class DietActivity extends AppCompatActivity implements DefaultView
{
    private ListView listView;
    MyCalendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_1);
        loadInput();
        loadDefaultView();
        myCalendar = new MyCalendar(DietActivity.this, DietActivity.this, R.id.calendarViewDietActivity, listView);

        onListViewItemSelected();
    }

    public void loadInput()
    {
        listView = findViewById(R.id.listViewDiet);
    }

    @Override
    public void loadDefaultView()
    {
        ActivityView activityView = new ActivityView(DietActivity.this, getApplicationContext(), this);
        activityView.statusBarColor(R.id.toolbarDietActivity);
        activityView.showBackButton();
    }


    //    private void weekCalendar(Calendar endDate, Calendar startDate)
    //    {
    //        calendar = new HorizontalCalendar.Builder(DietActivity.this, R.id.calendarViewDietActivity).defaultSelectedDate(cfg.selectedDate(DateGenerator.getSelectedDate()))
    //
    //                       .startDate(startDate.getTime())
    //                       .endDate(endDate.getTime())
    //                       .datesNumberOnScreen(5)
    //                       .dayNameFormat("EE")
    //                       .dayNumberFormat("dd")
    //                       .showDayName(true)
    //                       .showMonthName(false)
    //                       .build();
    //
    //        calendar.setCalendarListener(
    //            new HorizontalCalendarListener() {
    //                @Override
    //                public void onDateSelected(Date date, int position) {
    //                    dateFormat = cfg.getDateFormat().format(date.getTime());
    //                    dateFormatView = cfg.getDateFormatView().format(date.getTime());
    //                    tvDate.setText(dateFormatView);
    //
    //                    list.clear();
    //                    listView.setAdapter(adapter);
    //
    //                    DietDTO dto = new DietDTO();
    //                    dto.userName = SaveSharedPreference.getUserName(DietActivity.this);
    //                    dto.dateToday = dateFormat;
    ////                    loadAsynchTask(dto, DietActivity.this);
    //                    Log.i(TAG, "onDateSelected: " + dateFormat);
    //                }
    //            }
    //        );
    //    }

    private void onListViewItemSelected()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                TextView productId = view.findViewById(R.id.dietMealID);
                TextView productWeight = view.findViewById(R.id.dietMealWeight);
                TextView productTimeStamp = view.findViewById(R.id.dietTimeStamp);

                Intent intent = new Intent(DietActivity.this, DietProductShowActivity.class);
                intent.putExtra("productID", Integer.valueOf(productId.getText().toString()));
                intent.putExtra("dateFormat", DateGenerator.getSelectedDate());
                intent.putExtra("productWeight", Double.valueOf(productWeight.getText().toString()));
                intent.putExtra("productTimeStamp", productTimeStamp.getText().toString());
                intent.putExtra("previousActivity", "DietActivity");

                DietActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        new Provider(this, this, listView).load();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diet_1_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_search_product:
            Intent intent = new Intent(DietActivity.this,DietProductSearchActivity.class);
                intent.putExtra("dateFormat", DateGenerator.getSelectedDate());
            DietActivity.this.startActivity(intent);
            break;
        }
        return super.onOptionsItemSelected(item);
    }

}
