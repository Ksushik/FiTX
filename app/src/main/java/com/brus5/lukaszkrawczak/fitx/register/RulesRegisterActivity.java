package com.brus5.lukaszkrawczak.fitx.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.R;

/**
 * This class is responsible for accept rules of application.
 *
 * If user wont accept rules, he wont be able to register.
 */
public class RulesRegisterActivity extends AppCompatActivity
{
    private boolean isAccepted;

    /** Creating checkBox for accepting rules */
    private void acceptRulesCheckBox()
    {
        CheckBox checkBox = findViewById(R.id.acceptRulesRegisterCheckBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                isAccepted = isChecked;
            }
        });
    }

    /**  Creating rules downloaded from DB */
    private void rulesText()
    {
        TextView textView = findViewById(R.id.registerRulesTextView);
        textView.setText(getResources().getString(R.string.long_text_example));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_register);
        acceptRulesCheckBox();
        rulesText();
    }

    /** Creating Menu Button */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_save_user,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /** What happens when clicked item on Menu Button */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_save_user:
                if (isAccepted)
                {
                    Intent intent = new Intent(RulesRegisterActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
                else Toast.makeText(this, getResources().getString(R.string.must_accept_rules), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
