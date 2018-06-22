package com.baccouche.moez.jobfinder.Register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baccouche.moez.jobfinder.Retrofit.ConfigRetrofit;
import com.baccouche.moez.jobfinder.Retrofit.IUser;
import com.baccouche.moez.jobfinder.Model.User;
import com.baccouche.moez.jobfinder.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ASUS on 18/02/2018.
 */

public class RegisterProfileDetailsActivity extends Activity {

    private EditText etMail;
    private EditText etPhoneNumber;
    private EditText etCity;
    private EditText etStreet;
    private Button btNext;


    ConfigRetrofit configRetrofit = new ConfigRetrofit();
    Retrofit retrofit = configRetrofit.getConfig();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_details);



        final String NAME = getIntent().getStringExtra("name");
        final String FIRSTNAME = getIntent().getStringExtra("firstName");
        final String BIRTHDATE = getIntent().getStringExtra("date");
        final String GENDER = getIntent().getStringExtra("gender");
        final String PICTURE = getIntent().getStringExtra("pic");




        etMail = (EditText) findViewById(R.id.etMail);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etCity = (EditText) findViewById(R.id.etCity);
        btNext = (Button) findViewById(R.id.btNext);
        etStreet = (EditText) findViewById(R.id.etStreet);


        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etMail.getText().toString();
                final String number = etPhoneNumber.getText().toString();

                String street = etStreet.getText().toString();
                String city = etCity.getText().toString();

                final String address = street + ", " + city;

                IUser user = retrofit.create(IUser.class);


                if(email.equals(""))
                {
                    etMail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);
                    Toast.makeText(RegisterProfileDetailsActivity.this, "Veuillez saisir votre Email SVP !", Toast.LENGTH_LONG).show();
                }
                else
                    if(!checkfEmail(email))
                    {
                        etMail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);
                        Toast.makeText(RegisterProfileDetailsActivity.this, "Email incorrect", Toast.LENGTH_LONG).show();
                    }
                    else
                        if(number.length() != 8 || number.equals(""))
                    {
                        etMail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                        etPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);
                        Toast.makeText(RegisterProfileDetailsActivity.this, "Veuillez saisir un numéro de téléphone correct SVP", Toast.LENGTH_LONG).show();
                    }
                    else
                        if(street.equals(""))
                        {
                            etPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                            etStreet.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);
                            Toast.makeText(RegisterProfileDetailsActivity.this, "Saissez votre adresse SVP !", Toast.LENGTH_LONG).show();
                        }
                        else
                            if(city.equals(""))
                            {
                                etCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);
                                Toast.makeText(RegisterProfileDetailsActivity.this, "Saisissez votre Ville SVP !", Toast.LENGTH_SHORT).show();
                            }


                else
                    {
                        etMail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                        etPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                        etCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                        etStreet.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);




                    Call<User> call = user.getUserByEmail(email);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {


                            try {
                                Toast.makeText(RegisterProfileDetailsActivity.this, response.body().getEmail().toString() + " est déjà associé à un autre compte ! Veuillez saisir un autre Email SVP", Toast.LENGTH_LONG).show();
                                etMail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);
                            } catch (Exception e) {


                                Intent intent = new Intent(RegisterProfileDetailsActivity.this, RegisterProfilePersonalDetailsActivity.class);
                                intent.putExtra("email", email);
                                intent.putExtra("number", number);
                                intent.putExtra("address", address);
                                intent.putExtra("name", NAME);
                                intent.putExtra("firstName", FIRSTNAME);
                                intent.putExtra("date", BIRTHDATE);
                                intent.putExtra("gender", GENDER);
                                intent.putExtra("pic", PICTURE);

                                startActivity(intent);


                            }


                        }


                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(RegisterProfileDetailsActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }
        });


    }





    public boolean checkfEmail(String email)
    {
        int i = email.indexOf('@');
        int j = email.indexOf('.', i + 1);
        int k = email.indexOf('@', i + 1);

        if(i == -1 || j == -1 || k != -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


}


