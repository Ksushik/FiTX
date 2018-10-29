package com.brus5.lukaszkrawczak.fitx.register;

import android.view.View;
import android.widget.RadioGroup;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.register.search.SexListener;

/**
 * This class is responsible for showing up TWO radio buttons.
 * User MUST select one of the RadioButtons
 */
class RadioGroupTwo
{
    private String sex;
    boolean isSelected;

    RadioGroupTwo(View view, final SexListener sexListener)
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
                        isSelected = true;
                        break;
                    case R.id.radioManRegister:
                        sex = "m";
                        isSelected = true;
                        break;
                }

                sexListener.callBackSexChoose(sex);
            }
        });
    }
}
