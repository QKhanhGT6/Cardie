package com.example.cardie;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

public class ExperimentActivity extends Activity {

    @Override
    public void onBackPressed() {
        boolean flag = false;
        if (flag) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experiment_layout);
        String imageurl = "https://placekitten.com/200/300";
        ImageView imgView = (ImageView) findViewById(R.id.iv_experiment);
        Picasso.get()
                .load(imageurl)
                .fit()
                .centerCrop()
                .into(imgView);
    }
}
