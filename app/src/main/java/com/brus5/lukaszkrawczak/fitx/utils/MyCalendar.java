package com.brus5.lukaszkrawczak.fitx.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.async.provider.Provider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class MyCalendar
{
    private static final String TAG = "MyCalendar";
    private ListView listView;
    private HorizontalCalendar calendar;
    private Activity activity;
    private int resId;
    private Context context;
    private DateGenerator dateGenerator = new DateGenerator();
    private MyFloatingMenu mfm;
    /**
     * This is default constructor of MyCalendar
     *
     * @param activity Activity of UI
     * @param context  Context of UI
     * @param resId    resources ID of HorizontalCalendar
     * @param listView listView where view should be showed
     */
    public MyCalendar(Activity activity, Context context, int resId, ListView listView)
    {
        this.activity = activity;
        this.context = context;
        this.resId = resId;
        this.listView = listView;

        // This object must be above week
        mfm = new MyFloatingMenu(context);


        /**
         * The constructor automatically runs up method with past dateGenerator and future dateGenerator as a parameters.
         */
        weekCalendar(dateGenerator.calendarPast(), dateGenerator.calendarFuture());
        onSlide();
    }

    /**
     * weekCalendar shows few days on the horizontal view where can be scrolled
     *
     * @param calendarPast   This is where is the oldest dateGenerator of HorizontalCalendar. If we got for example:
     *                       5 days showed: 25, 26, 27, 28, 29 so the calendarPast is the 25th
     * @param calendarFuture This is where is the newest dateGenerator of HorizontalCalendar.
     *                       If we got like example above the newest day is 29th.
     */
    private void weekCalendar(Calendar calendarPast, Calendar calendarFuture)
    {
        Log.d(TAG, "weekCalendar() called with: calendarPast = [" + calendarPast.getTime() + "], calendarFuture = [" + calendarFuture.getTime() + "]");
        calendar = new HorizontalCalendar.Builder(((Activity)context), resId)
                .defaultSelectedDate(DateGenerator.savedDate())
                .startDate(calendarFuture.getTime())
                .endDate(calendarPast.getTime())
                .datesNumberOnScreen(5)
                .dayNameFormat("EE")
                .dayNumberFormat("dd")
                .showDayName(true)
                .showMonthName(false)
                .build();

        /**
         * Sets Listener of HorizontalCalendar. As parameter we've got abstract class HorizontalCalendarListener
         * which got following methods: onDateSelected, onCalendarScroll, onDateLongClicked.
         */
        calendar.setCalendarListener(new HorizontalCalendarListener()
        {
            @Override
            public void onDateSelected(Date date, int position)
            {
                showProgressBar();
                DateGenerator.setSelectedDate(MyCalendar.this.dateGenerator.getDateFormat().format(date.getTime()));
                Log.i(TAG, "setSelectedDate: " + DateGenerator.getSelectedDate());
                postDelayed();

                Log.d(TAG, "onDateSelected() called with: dateGenerator = [" + date + "], position = [" + position + "]");
                mfm.close();
            }
        });
    }

    private void postDelayed()
    {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //Do something after 20000ms
                new Provider(context, listView).load();
            }
        }, 1000);
    }

    private void showProgressBar()
    {
        listView.invalidateViews();
        ProgressBar pb = ((Activity)context).findViewById(R.id.progressBarListView);
        pb.setVisibility(View.VISIBLE);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onSlide()
    {
        listView.setOnTouchListener(new OnSwipeTouchListener(context)
        {
            public void onSwipeTop() {}
            public void onSwipeRight()
            {
                String sDate;  // Start dateGenerator
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
                Calendar c = Calendar.getInstance();

                try
                {
                    c.setTime(sdf.parse(DateGenerator.getSelectedDate()));
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }

                c.add(Calendar.DATE, -1);  // number of days to add
                sDate = sdf.format(c.getTime());  // dt is now the new dateGenerator

                Log.d(TAG, "onSwipeRight() called " +
                        "sdf: " + String.valueOf(sDate) + " :: " +
                        "");

                try
                {
                    calendar.selectDate(MyCalendar.this.dateGenerator.getDateFormat().parse(sDate), false);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }

            public void onSwipeLeft()
            {

                String sDate;  // Start dateGenerator
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
                Calendar c = Calendar.getInstance();

                try
                {
                    c.setTime(sdf.parse(DateGenerator.getSelectedDate()));
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }

                c.add(Calendar.DATE, 1);  // number of days to add
                sDate = sdf.format(c.getTime());  // dt is now the new dateGenerator

                Log.d(TAG, "onSwipeRight() called " +
                        "sdf: " + String.valueOf(sDate) + " :: " +
                        "");
                try
                {
                    calendar.selectDate(MyCalendar.this.dateGenerator.getDateFormat().parse(sDate), false);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
            public void onSwipeBottom()
            {
                new Provider(context, listView).load();
                showProgressBar();
                Toast.makeText(context, activity.getString(R.string.content_refresh), Toast.LENGTH_SHORT).show();
            }

            public boolean onTouch(View v, MotionEvent event)
            {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

}
