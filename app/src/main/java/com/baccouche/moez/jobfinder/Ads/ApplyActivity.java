package com.baccouche.moez.jobfinder.Ads;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baccouche.moez.jobfinder.HomeActivity;
import com.baccouche.moez.jobfinder.Model.Application;
import com.baccouche.moez.jobfinder.R;
import com.baccouche.moez.jobfinder.Retrofit.ConfigRetrofit;
import com.baccouche.moez.jobfinder.Retrofit.IUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ASUS on 10/03/2018.
 */

public class ApplyActivity extends Activity {
    
    
    private Button btValid;
    private Button btReturn;
    
    private EditText etNote;

     int ID;
     int idUser;
     int IDOWNER;
     public boolean result ;
    
    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);


        
        btValid = (Button) findViewById(R.id.btValid);
        btReturn = (Button) findViewById(R.id.btReturn);
        
        etNote = (EditText) findViewById(R.id.etNote);

        SharedPreferences prefUser = getApplicationContext().getSharedPreferences("userInfo", 0);
        idUser = prefUser.getInt("idUser", 0);


        SharedPreferences prefAd = getApplicationContext().getSharedPreferences("adInfo", 0);
        ID = prefAd.getInt("idAd", 0);

        IDOWNER = prefAd.getInt("idOwner", 0);


        
        btValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 checkAlreadyApplied();
                //Toast.makeText(ApplyActivity.this, "id Ad : " + ID + " idOwner : " + IDOWNER, Toast.LENGTH_SHORT).show();

            }
        });


        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApplyActivity.this, AdDetails.class);
                startActivity(intent);
            }
        });
        
        
    }

    private void validApplication()
    {

        String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());

        String dateApplication = timeStamp;


            String note = etNote.getText().toString();
            String status = "En attente";

            Application application = new Application(note, status, idUser, ID, IDOWNER, dateApplication);

            IUser userApp = retrofit.create(IUser.class);
            Call<Application> call = userApp.addApplication(application);

            call.enqueue(new Callback<Application>() {
                @Override
                public void onResponse(Call<Application> call, Response<Application> response) {
                    if(response.body().getNote().toString() != null)
                    {
                        Toast.makeText(ApplyActivity.this, "Demande d'emploi envoyée avec succés", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ApplyActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }

                }

                @Override
                public void onFailure(Call<Application> call, Throwable t) {
                    Toast.makeText(ApplyActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

    }


    private void checkAlreadyApplied()
    {
        

        IUser user = retrofit.create(IUser.class);
        Call<Application> checkCall = user.checkApplication(idUser, ID);
        checkCall.enqueue(new Callback<Application>() {
            @Override
            public void onResponse(Call<Application> call, Response<Application> response) {
                if(response.body().getStatus()!= null)
                {
                    String status = response.body().getStatus().toString();
                    if(status.equals("En attente"))
                    {
                        Toast.makeText(ApplyActivity.this, "Vous avez déjà envoyé une demande pour cette annonce !", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    validApplication();
                }
            }

            @Override
            public void onFailure(Call<Application> call, Throwable t) {
                Toast.makeText(ApplyActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        
    }


}
