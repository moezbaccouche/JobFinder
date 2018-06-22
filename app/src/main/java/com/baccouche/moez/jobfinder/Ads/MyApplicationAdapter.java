package com.baccouche.moez.jobfinder.Ads;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baccouche.moez.jobfinder.Model.Application;
import com.baccouche.moez.jobfinder.R;
import com.baccouche.moez.jobfinder.Retrofit.ConfigRetrofit;

import java.io.File;
import java.util.List;

import retrofit2.Retrofit;

/**
 * Created by ASUS on 11/03/2018.
 */


public class MyApplicationAdapter extends ArrayAdapter<Application> {

    private Context context;
    private List<Application> listApps;


    public MyApplicationAdapter(Context context, List<Application> listApps) {
        super(context, R.layout.custom_layout_my_applications, listApps);
        this.context = context;
        this.listApps = listApps;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.custom_layout_my_applications, parent, false);


        Application app = listApps.get(position);


        ImageView imAd = (ImageView) convertView.findViewById(R.id.imAd);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
        TextView tvSalary = (TextView) convertView.findViewById(R.id.tvSalary);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
        TextView tvDateApplication = (TextView) convertView.findViewById(R.id.tvDateApplication);


        String pictures = app.getPictures();
        String title = app.getTypeAd();
        double salary = app.getSalary();
        String payment = app.getPayment();
        String status = app.getStatus();
        String dateApplication = app.getDateApplication();
        String address = app.getAddress();


        File imgFile = new File(pictures);
        if (imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imAd.setImageBitmap(bitmap);
        } else {
            Log.v("IMAGE NOT FOUND", "Applicant IMAGE NOT FOUND");
        }

        if(status.equals("En attente"))
        {
            tvStatus.setTextColor(Color.BLUE);
        }
        else
            if(status.equals("Accepté"))
            {
                tvStatus.setTextColor(Color.GREEN);
            }
            else
            {
                tvStatus.setTextColor(Color.RED);
            }



        tvAddress.setText(address);
        tvTitle.setText(title);
        tvDateApplication.setText("Demande envoyée le " + dateApplication);
        tvStatus.setText(status);
        tvSalary.setText(salary + " Dt/" + payment);


        return convertView;
    }
}

