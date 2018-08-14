package com.brus5.lukaszkrawczak.fitx.Diet;

import android.content.Context;

import com.brus5.lukaszkrawczak.fitx.DTO.DietDTO;

public interface DietAsynchServiceDTO
{
    void updateCalories(DietDTO dto, Context context);
    void deleteCalories(DietDTO dto, Context context);
    void updateProductWeight(DietDTO dto, Context context);
    void insert(DietDTO dto, Context context);
    void delete(DietDTO dto, Context context);
}
