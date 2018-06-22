package com.baccouche.moez.jobfinder.Profile;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.baccouche.moez.jobfinder.R;

/**
 * Created by ASUS on 03/03/2018.
 */

public class PersonalDetailsActivity extends Activity {

    private TextView tvGender;
    private TextView tvBirthDate;
    private TextView tvDegree;
    private TextView tvStatus;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2_personal_details);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);


        tvGender = (TextView) findViewById(R.id.tvGender);
        tvBirthDate = (TextView) findViewById(R.id.tvBirthDate);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvDegree = (TextView) findViewById(R.id.tvDegree);


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
