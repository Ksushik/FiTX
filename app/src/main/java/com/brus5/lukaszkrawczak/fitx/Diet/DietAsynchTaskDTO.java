package com.brus5.lukaszkrawczak.fitx.Diet;

import android.content.Context;

import com.brus5.lukaszkrawczak.fitx.DTO.DietDTO;

public interface DietAsynchTaskDTO
{
    void loadAsynchTask(DietDTO dietDTO, Context ctx);
}
