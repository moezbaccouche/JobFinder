package com.baccouche.moez.jobfinder.Register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baccouche.moez.jobfinder.R;


/**
 * Created by ASUS on 25/02/2018.
 */

public class RegisterBirthdateActivity extends Activity {

    private Button btNext;
    private DatePicker calendar;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView tv_incorrect;
    private int selectedId = -1;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_birthdate);

        btNext = (Button) findViewById(R.id.btNext);
        calendar = (DatePicker) findViewById(R.id.vCalendar);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        tv_incorrect = (TextView) findViewById(R.id.tv_incorrect);

        final String NAME = getIntent().getStringExtra("name");
        final String FIRSTNAME = getIntent().getStringExtra("firstName");
        final String PATH = getIntent().getStringExtra("path");
        final String PICTURE = getIntent().getStringExtra("pic");





        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int year = calendar.getYear();
                final int day = calendar.getDayOfMonth();
                final int month = calendar.getMonth() + 1;

                final String birthdate = day + "/" + month + "/" + year;

                selectedId = radioGroup.getCheckedRadioButtonId();

                radioButton = (RadioButton) findViewById(selectedId);



                if(selectedId == -1)
                {
                    Toast.makeText(RegisterBirthdateActivity.this, "Veuillez sélectionner votre genre SVP !", Toast.LENGTH_SHORT).show();
                    tv_incorrect.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);
                }
                else
                {
                    tv_incorrect.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                    final String gender = radioButton.getText().toString();

                    Intent intent = new Intent(RegisterBirthdateActivity.this, RegisterProfileDetailsActivity.class);
                    intent.putExtra("date", birthdate);
                    intent.putExtra("name", NAME);
                    intent.putExtra("firstName", FIRSTNAME);
                    intent.putExtra("gender", gender);
                    intent.putExtra("pic", PICTURE);
                    startActivity(intent);
                }

            }
        });





    }
}
