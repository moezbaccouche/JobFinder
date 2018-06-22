package com.baccouche.moez.jobfinder.Ads;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
 * Created by ASUS on 04/03/2018.
 */

public class AdListAdapter extends ArrayAdapter<Ad> {

    private Context context;
    private List<Ad> listAds;


    public AdListAdapter(Context context, List<Ad> listAds)
    {
        super(context, R.layout.custom_layout, listAds);
        this.context = context;
        this.listAds = listAds;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.custom_layout, parent, false);


        Ad ad = listAds.get(position);

        TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
        TextView tvSalary = (TextView) convertView.findViewById(R.id.tvSalary);
        TextView tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
        ImageView im_Ad = (ImageView) convertView.findViewById(R.id.im_Ad);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        String path = ad.getPictures();
        File imgFile = new File(path);
        if(imgFile.exists())
        {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            im_Ad.setImageBitmap(bitmap);
        }

        tvDescription.setText(ad.getDescription());
        tvSalary.setText(String.valueOf(ad.getSalary()) + " Dt/" + ad.getPayment() );
        tvAddress.setText(ad.getAddress());
        tvTitle.setText(ad.getTypeAd());



        return convertView;
    }

}
