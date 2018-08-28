package com.brus5.lukaszkrawczak.fitx.Async.Protocol;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.DTO.MainDTO;

class TrainingActivityAsyncPreparator extends AsyncPreparator
{
    private MainDTO dto = new MainDTO();
    String params = "?user_id=" + dto.userID + "&date=" + dto.date;
    private String link = "http://justfitx.xyz/Training/ShowByUser.php";

    TrainingActivityAsyncPreparator(Activity activity, Context context, ListView listView)
    {
        super(activity, context, listView);
        startAsyncTask(link, params);
    }
}
