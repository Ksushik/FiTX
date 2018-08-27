package com.brus5.lukaszkrawczak.fitx.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.Async.LoadData;
import com.brus5.lukaszkrawczak.fitx.DTO.MainDTO;
import com.brus5.lukaszkrawczak.fitx.SaveSharedPreference;

public class AsyncPreparator
{
    private static final String TAG = "AsyncPreparator";
    ListView listView;
    private MainDTO dto = new MainDTO();
    private Context context;

    AsyncPreparator(Context context, ListView listView)
    {
        this.context = context;
        this.listView = listView;

        dto.userID = SaveSharedPreference.getUserID(context);
        dto.date = DateGenerator.getDate();

        String params = "?user_id=" + dto.userID + "&date=" + dto.date;
        Log.i(TAG, "AsyncPreparator: " + params);


        final LoadData loadData = new LoadData(context, listView);
        loadData.execute(params);

    }



}


