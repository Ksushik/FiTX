package com.brus5.lukaszkrawczak.fitx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ConnectionFailedActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_failed);

        ImageView imageView = findViewById(R.id.connectionFailedRefreshImageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
                Intent intent = new Intent(ConnectionFailedActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
