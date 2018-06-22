package com.baccouche.moez.jobfinder.Ads;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baccouche.moez.jobfinder.HomeActivity;
import com.baccouche.moez.jobfinder.Model.Ad;
import com.baccouche.moez.jobfinder.R;
import com.baccouche.moez.jobfinder.Retrofit.ConfigRetrofit;
import com.baccouche.moez.jobfinder.Retrofit.IUser;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ASUS on 05/03/2018.
 */

public class PlusAdActivityPart2 extends AppCompatActivity {

    private Spinner spDifficulty;
    private EditText etSalary;
    private EditText etAddress;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView tv16;
    private Button btNext;
    private int difficultyNumber;

    public double salary;

    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();

    int selectedId = -1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus_ad_part_2);

        final String TITLE = getIntent().getStringExtra("title");
        final String FIELD = getIntent().getStringExtra("field");
        final String DESCRIPTION = getIntent().getStringExtra("description");
        final String PATH = getIntent().getStringExtra("path");

        final String PICTURE = getIntent().getStringExtra("pic");

        final int IDUSER = getIntent().getIntExtra("idUser", 0);

        etSalary = (EditText) findViewById(R.id.etSalary);
        etAddress = (EditText) findViewById(R.id.etAddress);
        tv16 = (TextView) findViewById(R.id.textView16);

        btNext = (Button) findViewById(R.id.btNext);

        spDifficulty = (Spinner) findViewById(R.id.spDifficulty);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String address = etAddress.getText().toString();
                IUser iuserAd = retrofit.create(IUser.class);

                selectedId = radioGroup.getCheckedRadioButtonId();

                radioButton = (RadioButton) findViewById(selectedId);

                try {
                    salary = Double.parseDouble(etSalary.getText().toString());
                }
                catch (Exception e)
                {
                    Toast.makeText(PlusAdActivityPart2.this, "Veuillez saisir un salaire correct SVP !", Toast.LENGTH_LONG).show();
                }
                    final String payment = radioButton.getText().toString();
                    final String difficulty = spDifficulty.getSelectedItem().toString();
                    if(difficulty.equals("Facile"))
                    {
                        difficultyNumber = 1;
                    }
                    else
                        if(difficulty.equals("Moyenne"))
                        {
                            difficultyNumber = 2;
                        }
                        else
                            if(difficulty.equals("Difficile"))
                            {
                                difficultyNumber = 3;
                            }


                if(selectedId == -1)
                {
                    Toast.makeText(PlusAdActivityPart2.this, "Veuillez sélectionner le type de paiement SVP !", Toast.LENGTH_LONG).show();
                    tv16.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);

                }
                else
                   if(address.equals(""))
                    {
                        Toast.makeText(PlusAdActivityPart2.this, "Veuillez saisir votre adresse SVP !", Toast.LENGTH_SHORT).show();
                        etAddress.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);
                    }
                else
                {
                    tv16.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                    etAddress.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);



                    int isValid = 1;

                    String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());

                    String dateLastEdit = timeStamp;
                    Toast.makeText(PlusAdActivityPart2.this, "Date of the day : " + dateLastEdit, Toast.LENGTH_SHORT).show();


                    //Edited constructor PICTURE instead of PATH
                    Ad ad = new Ad(
                            salary,
                            difficulty,
                            TITLE,
                            FIELD,
                            DESCRIPTION,
                            PICTURE,
                            address,
                            IDUSER,
                            isValid,
                            dateLastEdit,
                            difficultyNumber,
                            payment
                    );



                    Call<Ad> call = iuserAd.plusAd(ad);
                    call.enqueue(new Callback<Ad>() {
                        @Override
                        public void onResponse(Call<Ad> call, Response<Ad> response) {
                            Toast.makeText(PlusAdActivityPart2.this, "Ajout de l'annonce " + response.body().getTypeAd().toString() + " effectué", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PlusAdActivityPart2.this, HomeActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<Ad> call, Throwable t) {
                            Toast.makeText(PlusAdActivityPart2.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });



                }



            }
        });



    }
}
