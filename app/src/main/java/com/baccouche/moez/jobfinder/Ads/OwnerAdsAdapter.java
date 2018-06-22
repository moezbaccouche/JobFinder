package com.baccouche.moez.jobfinder.Ads;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baccouche.moez.jobfinder.Model.Ad;
import com.baccouche.moez.jobfinder.R;

import java.io.File;
import java.util.List;

/**
 * Created by ASUS on 11/03/2018.
 */

public class OwnerAdsAdapter extends ArrayAdapter<Ad> {

    private Context context;
    private List<Ad> listAds;

    String status = "";


    public OwnerAdsAdapter(Context context, List<Ad> listAds)
    {
        super(context, R.layout.custom_layout_owner_ad, listAds);
        this.context = context;
        this.listAds = listAds;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.custom_layout_owner_ad, parent, false);


        Ad ad = listAds.get(position);


        TextView tvSalary = (TextView) convertView.findViewById(R.id.tvSalary);
        TextView tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
        ImageView im_Ad = (ImageView) convertView.findViewById(R.id.imAd);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);

        String path = ad.getPictures();
        File imgFile = new File(path);
        if(imgFile.exists())
        {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            im_Ad.setImageBitmap(bitmap);
        }


        int isValid = ad.getIsValid();
        if(isValid == 0)
        {
            status = "Terminée";
        }
        else
        {
            status = "Valide";
        }

        if(status.equals("Valide"))
        {
            tvStatus.setTextColor(Color.GREEN);
        }
        else
        {
            tvStatus.setTextColor(Color.RED);
        }


        tvStatus.setText(status);
        tvSalary.setText(String.valueOf(ad.getSalary()) + " Dt/" + ad.getPayment());
        tvAddress.setText(ad.getAddress());
        tvTitle.setText(ad.getTypeAd());



        return convertView;
    }

}
