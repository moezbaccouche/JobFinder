package com.baccouche.moez.jobfinder.Ads;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baccouche.moez.jobfinder.Model.Ad;
import com.baccouche.moez.jobfinder.Model.Application;
import com.baccouche.moez.jobfinder.R;
import com.baccouche.moez.jobfinder.Retrofit.ConfigRetrofit;
import com.baccouche.moez.jobfinder.Retrofit.IUser;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ASUS on 10/03/2018.
 */

public class AppListAdapter extends ArrayAdapter<Application> {

    private Context context;
    private List<Application> listApps;


    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();



    public AppListAdapter(Context context, List<Application> listApps)
    {
        super(context, R.layout.custom_layout_application, listApps);
        this.context = context;
        this.listApps = listApps;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.custom_layout_application, parent, false);



        Application app = listApps.get(position);


        TextView tvFullName = (TextView) convertView.findViewById(R.id.tvApplicantFullName);
        TextView tvNote = (TextView) convertView.findViewById(R.id.tvNote);

        ImageView imApplicant = (ImageView) convertView.findViewById(R.id.imApplicant);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvIdUser = (TextView) convertView.findViewById(R.id.tvIdUser);
        TextView tvIdAd = (TextView) convertView.findViewById(R.id.tvIdAd);


        String picture = app.getPicture();
        String firstName = app.getFirstName();
        String lastName = app.getLastName();
        String note = app.getNote();
        String adTitle = app.getTypeAd();
        int idUser = app.getIdUser();
        int idAd = app.getIdAd();


        File imgFile = new File(picture);
        if(imgFile.exists())
        {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imApplicant.setImageBitmap(bitmap);
        }
        else
        {
            Log.v("IMAGE NOT FOUND", "Applicant IMAGE NOT FOUND");
        }

        String fullName = firstName + " " + lastName;
        tvFullName.setText(fullName);
        tvNote.setText(note);
        tvTitle.setText(adTitle);
        tvIdUser.setText(String.valueOf(idUser));
        tvIdAd.setText(String.valueOf(idAd));

        tvIdUser.setVisibility(View.INVISIBLE);
        tvIdAd.setVisibility(View.INVISIBLE);


        return convertView;
    }




}
