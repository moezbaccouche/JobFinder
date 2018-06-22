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

import com.baccouche.moez.jobfinder.Model.Ad;
import com.baccouche.moez.jobfinder.Model.Application;
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

public class ApplicationsActivity extends Activity {

    private Button btAccept;
    private Button btReject;
    private Button btReturn;



    private ListView listView;
    private List<Application> listApps;

    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_applications);

        listView = (ListView) findViewById(R.id.listApps);




        SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);
        int idOwner = pref.getInt("idUser", 0);



        IUser iuser = retrofit.create(IUser.class);
        Call call = iuser.getApplicationsByOwnerId(idOwner);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                listApps = (List<Application>) response.body();
                listView.setAdapter(new AppListAdapter(getApplicationContext(), listApps));

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        final int idApplicant = listApps.get(position).getIdUser();
                        final int idAd = listApps.get(position).getIdAd();
                        final String noteApplicant = listApps.get(position).getNote();



                        SharedPreferences pref = getApplicationContext().getSharedPreferences("applicantInfo", 0);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putInt("idApplicant", idApplicant);
                        editor.putInt("idAd", idAd);
                        editor.putString("noteApplicant", noteApplicant);

                        editor.commit();




                        Intent intent = new Intent(ApplicationsActivity.this, ApplicationResponse.class);
                        startActivity(intent);

                    }
                });

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(ApplicationsActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }




}

