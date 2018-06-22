package com.baccouche.moez.jobfinder.Ads;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baccouche.moez.jobfinder.HomeActivity;
import com.baccouche.moez.jobfinder.Model.Application;
import com.baccouche.moez.jobfinder.Model.User;
import com.baccouche.moez.jobfinder.R;
import com.baccouche.moez.jobfinder.Retrofit.ConfigRetrofit;
import com.baccouche.moez.jobfinder.Retrofit.IUser;

import org.w3c.dom.Text;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ASUS on 11/03/2018.
 */

public class ApplicationResponse extends Activity{

    private Button btAccept;
    private Button btReject;
    private Button btReturn;


    private TextView tvName;
    private TextView tvApplicantFullName;

    private ImageView imApplicant;
    private TextView tvCurrentJob;
    private TextView tvEmail;
    private TextView tvAddress;
    private TextView tvPhone;
    private TextView tvNote;


    public String fullName = "";



    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();

    public int idApplicant;
    public int idAd;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_response);

        btAccept = (Button) findViewById(R.id.btAccept);
        btReject = (Button) findViewById(R.id.btReject);
        btReturn = (Button) findViewById(R.id.btReturn);


        tvName = (TextView) findViewById(R.id.tvName);
        tvApplicantFullName = (TextView) findViewById(R.id.tvApplicantFullName);
        tvCurrentJob = (TextView) findViewById(R.id.tvCurrentJob);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvEmail = (TextView) findViewById(R.id.tvApplicantEmail);

        tvPhone = (TextView) findViewById(R.id.tvApplicantPhone);
        tvNote = (TextView) findViewById(R.id.tvApplicantNote);


        imApplicant = (ImageView) findViewById(R.id.imApplicant);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("applicantInfo", 0);

        idApplicant = pref.getInt("idApplicant", 0);
        idAd = pref.getInt("idAd", 0);
        String noteApplicant = pref.getString("noteApplicant", "");
        tvNote.setText(noteApplicant);


        IUser user = retrofit.create(IUser.class);
        Call<User> call = user.getOwner(idApplicant);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                tvAddress.setText(response.body().getAddress());
                tvCurrentJob.setText(response.body().getCurrentJob());
                tvPhone.setText(response.body().getPhoneNumber());
                tvEmail.setText(response.body().getEmail());

                String firstName = response.body().getFirstName();
                String lastName = response.body().getLastName();
                fullName = firstName + " " + lastName;
                
                String picture = response.body().getPicture();
                
                File imgFile = new File(picture);
                if(imgFile.exists())
                {
                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imApplicant.setImageBitmap(bitmap);
                }
                else
                {
                    Toast.makeText(ApplicationResponse.this, "Image non disponible", Toast.LENGTH_LONG).show();
                }

                tvApplicantFullName.setText(fullName);

                tvName.setText(fullName);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ApplicationResponse.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        btAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptApplication();
            }
        });

        btReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectApplication();
            }
        });

        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApplicationResponse.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }


    public void acceptApplication()
    {
        IUser user = retrofit.create(IUser.class);
        Call<Application> call = user.acceptApplication(idApplicant, idAd);
        call.enqueue(new Callback<Application>() {
            @Override
            public void onResponse(Call<Application> call, Response<Application> response) {
                Toast.makeText(ApplicationResponse.this, "Félicitation vous venez d'accepter la demande de " + fullName , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ApplicationResponse.this, HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Application> call, Throwable t) {
                Toast.makeText(ApplicationResponse.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void rejectApplication()
    {
        IUser user = retrofit.create(IUser.class);
        Call<Application> call = user.rejectApplication(idApplicant, idAd);
        call.enqueue(new Callback<Application>() {
            @Override
            public void onResponse(Call<Application> call, Response<Application> response) {
                Toast.makeText(ApplicationResponse.this, "Vous avez refusé la demande de " + fullName, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ApplicationResponse.this, HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Application> call, Throwable t) {
                Toast.makeText(ApplicationResponse.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
