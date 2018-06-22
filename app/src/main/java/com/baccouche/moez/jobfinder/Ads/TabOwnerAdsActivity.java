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
import com.baccouche.moez.jobfinder.R;
import com.baccouche.moez.jobfinder.Retrofit.ConfigRetrofit;
import com.baccouche.moez.jobfinder.Retrofit.IUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ASUS on 05/03/2018.
 */

public class TabOwnerAdsActivity extends Activity {

    private Button plusAd;
    private ListView listView;
    private List<Ad> listAds;

    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_owner_ads);

        plusAd = (Button) findViewById(R.id.plusAd);
        listView = (ListView) findViewById(R.id.listMyAds);

        plusAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TabOwnerAdsActivity.this, PlusAdActivityPart1.class);
                startActivity(intent);
            }
        });


        SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);

        int idOwner = pref.getInt("idUser", 0);

        IUser iuser = retrofit.create(IUser.class);
        Call call = iuser.getAdsByOwnerId(idOwner);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                listAds = (List<Ad>) response.body();
                listView.setAdapter(new OwnerAdsAdapter(getApplicationContext(), listAds));

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        final int idAd = listAds.get(position).getIdAd();


                        Intent intent = new Intent(TabOwnerAdsActivity.this, EditAdActivity.class);

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("adInfo", 0);

                        SharedPreferences.Editor editor = pref.edit();


                        editor.putInt("idAd", idAd);

                        editor.commit();
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(TabOwnerAdsActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

