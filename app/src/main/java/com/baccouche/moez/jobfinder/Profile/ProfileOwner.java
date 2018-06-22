package com.baccouche.moez.jobfinder.Profile;

import android.app.ActivityGroup;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.baccouche.moez.jobfinder.Retrofit.ConfigRetrofit;
import com.baccouche.moez.jobfinder.Retrofit.IUser;
import com.baccouche.moez.jobfinder.Model.User;
import com.baccouche.moez.jobfinder.R;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ASUS on 05/03/2018.
 */

public class ProfileOwner extends ActivityGroup {

    private ImageView im_Owner;
    private TextView tvFullName;
    private TextView tvJob;



    private Button btMenu;




    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_full);

       final int idOwner = getIntent().getIntExtra("idOwner", 0);

        btMenu = (Button) findViewById(R.id.buttonUserMenu);

        btMenu.setVisibility(View.GONE);


        im_Owner = (ImageView) findViewById(R.id.im_user);
        tvFullName = (TextView) findViewById(R.id.tvFullName);
        tvJob = (TextView) findViewById(R.id.tvJob);

        //View personalDetailsView = getLayoutInflater().inflate(R.layout.activity_tab2_personal_details, null);




        IUser user = retrofit.create(IUser.class);
        Call<User> call = user.getOwner(idOwner);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                String path = response.body().getPicture().toString();
                String firstName = response.body().getFirstName().toString();
                String lastName = response.body().getLastName().toString();
                String job = response.body().getCurrentJob().toString();
                String email = response.body().getEmail().toString();
                String status = response.body().getStatus().toString();
                String address = response.body().getAddress().toString();
                String phone = response.body().getPhoneNumber().toString();
                String gender = response.body().getGender().toString();
                String degree = response.body().getDegree().toString();
                String birthDate = response.body().getBirthDate().toString();

                String fullName = firstName + " " + lastName;

                File imgFile = new File(path);
                if(imgFile.exists())
                {
                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    im_Owner.setImageBitmap(bitmap);
                }
                else
                {
                    Toast.makeText(ProfileOwner.this, "Photo indisponible", Toast.LENGTH_SHORT).show();
                }

                tvFullName.setText(fullName);
                tvJob.setText(job);

                ProfileOwnerDetails.tvEmail.setText(email);
                ProfileOwnerDetails.tvAddress.setText(address);
                ProfileOwnerDetails.tvPhone.setText(phone);

                SharedPreferences pref = getApplicationContext().getSharedPreferences("ownerInfo", 0);
                SharedPreferences.Editor editor = pref.edit();

                editor.putString("gender", gender);
                editor.putString("birthdate", birthDate);
                editor.putString("status", status);
                editor.putString("degree", degree);

                editor.commit();


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileOwner.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        TabHost tab = (TabHost) findViewById(R.id.tabHost);
        tab.setup(getLocalActivityManager());
        TabHost.TabSpec spec1 = tab.newTabSpec("Coordonnées");
        spec1.setIndicator("Coordonnées");
        Intent intentDetails = new Intent(ProfileOwner.this, ProfileOwnerDetails.class);
        spec1.setContent(intentDetails);
        tab.addTab(spec1);

        TabHost.TabSpec spec2 = tab.newTabSpec("Détails personnels");
        spec2.setIndicator("Détails personnels");
        Intent intentPersonal = new Intent(ProfileOwner.this, ProfileOwnerPersonal.class);
        spec2.setContent(intentPersonal);
        tab.addTab(spec2);


    }
}
