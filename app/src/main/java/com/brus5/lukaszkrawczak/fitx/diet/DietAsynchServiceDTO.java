package com.brus5.lukaszkrawczak.fitx.diet;

import android.content.Context;

import com.brus5.lukaszkrawczak.fitx.dto.DietDTO;

public interface DietAsynchServiceDTO
{
    void updateCalories(DietDTO dto, Context context);
    void deleteCalories(DietDTO dto, Context context);
    void updateProductWeight(DietDTO dto, Context context);
    void insert(DietDTO dto, Context context);
    void delete(DietDTO dto, Context context);
}
