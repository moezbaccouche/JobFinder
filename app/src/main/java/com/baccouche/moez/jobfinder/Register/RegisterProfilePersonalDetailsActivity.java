package com.baccouche.moez.jobfinder.Register;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baccouche.moez.jobfinder.Retrofit.ConfigRetrofit;
import com.baccouche.moez.jobfinder.HomeActivity;
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

public class RegisterProfilePersonalDetailsActivity extends Activity {

    private EditText etDegree;
    private EditText etCurrentJob;
    private EditText etPassword;
    private EditText etCheckPw;
    private Spinner  spStatus;
    private Button btNext;
    private TextView tv_incorrect;

    public String job = "";
    public String status = "";
    public String degree = "";

    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit  = config.getConfig();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_personal_infos);

        final String NAME = getIntent().getStringExtra("name");
        final String FIRSTNAME = getIntent().getStringExtra("firstName");
        final String BIRTHDATE = getIntent().getStringExtra("date");
        final String EMAIL = getIntent().getStringExtra("email");
        final String PHONE = getIntent().getStringExtra("number");
        final String ADDRESS = getIntent().getStringExtra("address");
        final String GENDER = getIntent().getStringExtra("gender");
        final String PATH = getIntent().getStringExtra("path");

        //Added 10/03/2018
        final String PICTURE = getIntent().getStringExtra("pic");


        etDegree = (EditText) findViewById(R.id.etDegree);
        etCurrentJob = (EditText) findViewById(R.id.etCurrentJob);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etCheckPw = (EditText) findViewById(R.id.etCheckPw);

        btNext = (Button) findViewById(R.id.btNext);

        spStatus = (Spinner) findViewById(R.id.spStatus);

        tv_incorrect = (TextView) findViewById(R.id.tv_incorrect);

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                degree = etDegree.getText().toString();
                job = etCurrentJob.getText().toString();
                String pw = etPassword.getText().toString();
                String checkPw = etCheckPw.getText().toString();
                status = spStatus.getSelectedItem().toString();

                IUser user = retrofit.create(IUser.class);

                if(degree.equals(""))
                {
                    etDegree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);
                    Toast.makeText(RegisterProfilePersonalDetailsActivity.this, "Veuillez saisir votre diplôme SVP !", Toast.LENGTH_SHORT).show();
                }
                else
                    if(job.equals(""))
                    {
                        etDegree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                        etCurrentJob.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);
                        Toast.makeText(RegisterProfilePersonalDetailsActivity.this, "Veuillez saisir votre diplôme SVP !", Toast.LENGTH_SHORT).show();
                    }
                    else
                        if(pw.length() < 8)
                        {
                            etDegree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                            etCurrentJob.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                            Toast.makeText(RegisterProfilePersonalDetailsActivity.this, "Le mot de passe doit contenir au moins 8 caractères !", Toast.LENGTH_SHORT).show();
                            etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);
                        }
                        else
                            if(!pw.equals(checkPw))
                            {
                                etDegree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                                etCurrentJob.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                                Toast.makeText(RegisterProfilePersonalDetailsActivity.this, "Vérifier votre mot de passe SVP !", Toast.LENGTH_SHORT).show();
                                etCheckPw.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);
                            }

                            else
                            {
                                etDegree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                                etCurrentJob.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                                etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                                etCheckPw.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                                tv_incorrect.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);

                                //Edited constructor PICTURE instead Of PATH
                                User client = new User(
                                    NAME,
                                    FIRSTNAME,
                                    GENDER,
                                    ADDRESS,
                                    EMAIL,
                                    pw,
                                    job,
                                    degree,
                                    status,
                                    BIRTHDATE,
                                    PICTURE,
                                    PHONE
                            );

                             Intent intent = new Intent(RegisterProfilePersonalDetailsActivity.this, HomeActivity.class);
                            Call<User> call = user.addUser(client);
                            call.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    Toast.makeText(RegisterProfilePersonalDetailsActivity.this, "Inscription effectuée avec succès " + response.body().getLastName(), Toast.LENGTH_SHORT).show();

                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);
                                    final SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("lastName", NAME);
                                    editor.putString("firstName", FIRSTNAME);
                                    editor.putString("gender", GENDER);
                                    editor.putString("email", EMAIL);
                                    editor.putString("job", job);
                                    editor.putString("picture", PICTURE);
                                    editor.putString("address", ADDRESS);
                                    editor.putString("phone", PHONE);
                                    editor.putString("degree", degree);
                                    editor.putString("birthdate", BIRTHDATE);
                                    editor.putString("status", status);



                                    IUser userId = retrofit.create(IUser.class);



                                    Call<User> callId = userId.getUserByEmail(EMAIL);
                                    callId.enqueue(new Callback<User>() {
                                        @Override
                                        public void onResponse(Call<User> call, Response<User> response) {

                                             Intent intent = new Intent(RegisterProfilePersonalDetailsActivity.this, HomeActivity.class);

                                             editor.putInt("idUser", response.body().getIdUser());
                                             editor.commit();
                                             intent.putExtra("email", EMAIL);
                                             intent.putExtra("picture", PICTURE);
                                             startActivity(intent);
                                        }

                                        @Override
                                        public void onFailure(Call<User> call, Throwable t) {
                                            Toast.makeText(RegisterProfilePersonalDetailsActivity.this, "Failure :" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });



                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Toast.makeText(RegisterProfilePersonalDetailsActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
            }
        });
    }
}
