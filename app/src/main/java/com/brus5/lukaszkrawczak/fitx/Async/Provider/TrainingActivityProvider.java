package com.brus5.lukaszkrawczak.fitx.Async.Provider;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.DTO.MainDTO;

class TrainingActivityProvider extends Provider
{
    private MainDTO dto = new MainDTO();
    String params = "?user_id=" + dto.userID + "&date=" + dto.date;
    private String link = "http://justfitx.xyz/Training/ShowByUserShort.php";

    // TODO: 28.08.2018 zrobić tutaj cały provider tak jak w dietactivity również edytować skrypt w restApi
    TrainingActivityProvider(Activity activity, Context context, ListView listView)
    {
        super(activity, context, listView);
        startHTTPService(link, params);
    }
}
