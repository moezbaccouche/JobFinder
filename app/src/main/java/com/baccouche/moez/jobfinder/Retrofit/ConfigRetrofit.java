package com.baccouche.moez.jobfinder.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ASUS on 23/02/2018.
 */

public class ConfigRetrofit {

    public Retrofit getConfig()
    {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8888/")
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = builder.build();
        return retrofit;

    }
}
