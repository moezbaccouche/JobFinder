package com.baccouche.moez.jobfinder.Ads;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.baccouche.moez.jobfinder.HomeActivity;
import com.baccouche.moez.jobfinder.Model.Application;
import com.baccouche.moez.jobfinder.Profile.ProfileActivity;
import com.baccouche.moez.jobfinder.R;
import com.baccouche.moez.jobfinder.Retrofit.ConfigRetrofit;
import com.baccouche.moez.jobfinder.Retrofit.IUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ASUS on 11/03/2018.
 */

public class MyApplicationsActivity extends Activity {

    private ListView listView;
    private List<Application> listApps;
    private Button btReturn;

    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_my_applications);

        listView = (ListView) findViewById(R.id.listMyApps);

        btReturn = (Button) findViewById(R.id.btReturn);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);
        int idUser = pref.getInt("idUser", 0);



        IUser iuser = retrofit.create(IUser.class);
        Call call = iuser.getApplicationsByUserId(idUser);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                listApps = (List<Application>) response.body();
                listView.setAdapter(new MyApplicationAdapter(getApplicationContext(), listApps));

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(MyApplicationsActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplicationsActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }


}

