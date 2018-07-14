package com.brus5.lukaszkrawczak.fitx;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;


public class DTOAdapter extends AdapterView{
    DTO dto;
    Context ctx;

    public DTOAdapter(Context context) {
        super(context);


    }

    public DTO getDto() {
        return dto;
    }

    public void setDto(DTO dto) {
        this.dto = dto;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }


    @Override
    public Adapter getAdapter() {
        return null;
    }

    @Override
    public void setAdapter(Adapter adapter) {

    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setSelection(int position) {

    }
}
