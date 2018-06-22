package com.baccouche.moez.jobfinder.Profile;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.baccouche.moez.jobfinder.R;

/**
 * Created by ASUS on 05/03/2018.
 */

public class ProfileOwnerDetails extends Activity {

    public static TextView tvEmail;
    public static TextView tvAddress;
    public static TextView tvPhone;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1_details);


        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvPhone = (TextView) findViewById(R.id.tvNumber);



    }
}
