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

public class ProfileDetailsActivity extends Activity {

    private TextView tvAddress;
    private TextView tvNumber;
    private TextView tvEmail;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1_details);

        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvNumber = (TextView) findViewById(R.id.tvNumber);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);


        final String address = pref.getString("address", "");
        final String phone = pref.getString("phone", "");
        final String email = pref.getString("email", "");





        tvEmail.setText(email);
        tvAddress.setText(address);
        tvNumber.setText(phone);
    }
}
