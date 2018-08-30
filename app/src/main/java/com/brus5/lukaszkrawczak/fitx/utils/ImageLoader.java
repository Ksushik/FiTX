package com.brus5.lukaszkrawczak.fitx.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.brus5.lukaszkrawczak.fitx.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class ImageLoader
{
    private final String url;
    private Context context;
    private ImageView imageView;
    private ProgressBar progressBar;

    public ImageLoader(Context context, ImageView imageView, ProgressBar progrssBar, String url)
    {
        this.context = context;
        this.imageView = imageView;
        this.progressBar = progrssBar;
        this.url = url;

        load();
    }

    private void load()
    {
        Transformation transformation = new RoundedTransformationBuilder().borderColor(Color.BLACK).borderWidthDp(0).cornerRadiusDp(5).oval(false).build();


        Picasso.with(context).load(url).placeholder(null).transform(transformation).error(R.drawable.image_no_available).into(imageView, new com.squareup.picasso.Callback()
        {
            @Override
            public void onSuccess()
            {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError()
            {
                progressBar.setVisibility(View.INVISIBLE);
                DateGenerator cfg = new DateGenerator();
                cfg.showError(context);
            }
        });
    }
}
