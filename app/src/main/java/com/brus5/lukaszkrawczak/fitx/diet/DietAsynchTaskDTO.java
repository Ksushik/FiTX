package com.brus5.lukaszkrawczak.fitx.diet;

import android.content.Context;

import com.brus5.lukaszkrawczak.fitx.dto.DietDTO;

public interface DietAsynchTaskDTO
{
    void loadAsynchTask(DietDTO dietDTO, Context context);
}
