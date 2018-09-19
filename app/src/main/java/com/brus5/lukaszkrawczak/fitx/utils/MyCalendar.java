package com.brus5.lukaszkrawczak.fitx.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.MainService;
import com.brus5.lukaszkrawczak.fitx.async.provider.Provider;

import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class MyCalendar
{
    private static final String TAG = "MyCalendar";
    ListView listView;
    private HorizontalCalendar calendar;
    private Activity activity;
    private int resId;
    private Context context;
    private DateGenerator date = new DateGenerator();

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

        /**
         * The constructor automatically runs up method with past date and future date as a parameters.
         */
        weekCalendar(date.calendarPast(), date.calendarFuture());

    }

    /**
     * weekCalendar shows few days on the horizontal view where can be scrolled
     *
     * @param calendarPast   This is where is the oldest date of HorizontalCalendar. If we got for example:
     *                       5 days showed: 25, 26, 27, 28, 29 so the calendarPast is the 25th
     * @param calendarFuture This is where is the newest date of HorizontalCalendar.
     *                       If we got like example above the newest day is 29th.
     */
    public void weekCalendar(Calendar calendarPast, Calendar calendarFuture)
    {
        calendar = new HorizontalCalendar.Builder(activity, resId).defaultSelectedDate(DateGenerator.savedDate()).startDate(calendarFuture.getTime()).endDate(calendarPast.getTime()).datesNumberOnScreen(5).dayNameFormat("EE").dayNumberFormat("dd").showDayName(true).showMonthName(false).build();

        /**
         * Sets Listener of HorizontalCalendar. As parameter we've got abstract class HorizontalCalendarListener
         * which got following methods: onDateSelected, onCalendarScroll, onDateLongClicked.
         */
        calendar.setCalendarListener(new HorizontalCalendarListener()
        {
            @Override
            public void onDateSelected(Date date, int position)
            {
                DateGenerator.setSelectedDate(MyCalendar.this.date.getDateFormat().format(date.getTime()));

                new Provider(context, listView).load();

                Log.d(TAG, "onDateSelected() called with: date = [" + date + "], position = [" + position + "]");
            }
        });
    }



}
