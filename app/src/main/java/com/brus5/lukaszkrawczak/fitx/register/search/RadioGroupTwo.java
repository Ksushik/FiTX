package com.brus5.lukaszkrawczak.fitx.register.search;

import android.view.View;
import android.widget.RadioGroup;

import com.brus5.lukaszkrawczak.fitx.R;

/**
 * This class is responsible for showing up TWO radio buttons.
 */
public class RadioGroupTwo
{
    private String sex;

    public RadioGroupTwo(View view, final SexListener sexListener)
    {

        RadioGroup radioGroup = (RadioGroup)view;

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.radioWomanRegister:
                        sex = "w";
                        break;
                    case R.id.radioManRegister:
                        sex = "m";
                        break;
                }

                sexListener.callBackSexChoose(sex);
            }
        });
    }
}
