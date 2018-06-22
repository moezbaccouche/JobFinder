package com.baccouche.moez.jobfinder.Ads;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baccouche.moez.jobfinder.Retrofit.ConfigRetrofit;
import com.baccouche.moez.jobfinder.HomeActivity;
import com.baccouche.moez.jobfinder.Retrofit.IUser;
import com.baccouche.moez.jobfinder.Model.Ad;
import com.baccouche.moez.jobfinder.Model.User;
import com.baccouche.moez.jobfinder.Profile.ProfileOwner;
import com.baccouche.moez.jobfinder.R;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ASUS on 04/03/2018.
 */

public class AdDetails extends Activity{

    private ImageView im_Owner;
    private ImageView im_Ad;

    private TextView tvDescription;
    private TextView tvSalary;
    private TextView tvAddress;
    private TextView tvLastEditDate;
    private TextView tvOwnerFullName;
    private TextView textView;

    private Button btApply;
    private Button btMessage;


    private Button btReturn;

    public int ID ;


    private int idOwner;

    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_details);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("adInfo", 0);
        final SharedPreferences.Editor editor = pref.edit();
        final int ID = pref.getInt("idAd", 0);

        SharedPreferences prefUser = getApplicationContext().getSharedPreferences("userInfo", 0);
        final int idUser = prefUser.getInt("idUser", 0);



        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvSalary = (TextView) findViewById(R.id.tvSalary);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvLastEditDate = (TextView) findViewById(R.id.tvLastEditDate);
        btReturn = (Button) findViewById(R.id.btReturn);
        tvOwnerFullName = (TextView) findViewById(R.id.tvOwnerFullName);

        im_Ad = (ImageView) findViewById(R.id.im_Ad);
        im_Owner = (ImageView) findViewById(R.id.im_Owner);

        btApply = (Button) findViewById(R.id.btApply);
        btMessage = (Button) findViewById(R.id.btMessage);

        textView = (TextView) findViewById(R.id.textView51);
        textView.setVisibility(View.INVISIBLE);


        IUser ad = retrofit.create(IUser.class);
        Call<Ad> call = ad.getAdById(ID);
        call.enqueue(new Callback<Ad>() {
            @Override
            public void onResponse(Call<Ad> call, Response<Ad> response) {
                String description = response.body().getDescription().toString();
                String address = response.body().getAddress().toString();
                double salary = response.body().getSalary();
                String payment = response.body().getPayment();
                String path = response.body().getPictures().toString();
                String dateLastEdit = response.body().getDateLastEdit();

                idOwner = response.body().getIdUser();
                editor.putInt("idOwner", idOwner);
                editor.commit();

                if(idOwner == idUser)
                {
                    btApply.setVisibility(View.INVISIBLE);
                    btMessage.setVisibility(View.INVISIBLE);
                }

                File imgFile = new File(path);
                if(imgFile.exists())
                {
                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    im_Ad.setImageBitmap(bitmap);
                }

                tvDescription.setText(description);
                tvSalary.setText(salary + " Dt/" + payment);
                tvAddress.setText(address);
                tvLastEditDate.setText("Créé le " + dateLastEdit + " par");
            }

            @Override
            public void onFailure(Call<Ad> call, Throwable t) {
                Toast.makeText(AdDetails.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        IUser user = retrofit.create(IUser.class);
        Call<User> callUser = user.getOwnerById(ID);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                String ownerLastName = response.body().getLastName().toString();
                String ownerFirstName = response.body().getFirstName().toString();

                String ownerFullName = ownerFirstName + " " + ownerLastName;

                String ownerPicture = response.body().getPicture().toString();



                File imgFile = new File(ownerPicture);
                if(imgFile.exists())
                {
                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    im_Owner.setImageBitmap(bitmap);
                }
                else
                {
                    Toast.makeText(AdDetails.this, "image introuvable propriétaire de l'annonce introuvable", Toast.LENGTH_SHORT).show();
                }

                tvOwnerFullName.setText(ownerFullName);


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(AdDetails.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdDetails.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        im_Owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdDetails.this, ProfileOwner.class);
                intent.putExtra("idOwner", idOwner);
                //Toast.makeText(AdDetails.this, "ID OWNER : " + idOwner, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });


        btApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply();
            }
        });
    }

    private void apply()
    {
        Intent intent = new Intent(AdDetails.this, ApplyActivity.class);

        startActivity(intent);
    }
}
