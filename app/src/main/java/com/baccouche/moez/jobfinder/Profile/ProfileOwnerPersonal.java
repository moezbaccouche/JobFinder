package com.baccouche.moez.jobfinder.Profile;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.baccouche.moez.jobfinder.R;


/**
 * Created by ASUS on 05/03/2018.
 */

public class ProfileOwnerPersonal extends Activity{


    public TextView tvGender;
    public TextView tvStatus;
    public TextView tvDegree;
    public TextView tvBirthDate;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2_personal_details);

        tvGender = (TextView) findViewById(R.id.tvGender);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvDegree = (TextView) findViewById(R.id.tvDegree);
        tvBirthDate = (TextView) findViewById(R.id.tvBirthDate);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("ownerInfo", 0);

        final String degree = pref.getString("degree", "");
        final String birthdate = pref.getString("birthdate", "");
        final String status = pref.getString("status", "");
        final String gender = pref.getString("gender", "");

        tvGender.setText(gender);
        tvBirthDate.setText(birthdate);
        tvStatus.setText(status);
        tvDegree.setText(degree);



    }


}
