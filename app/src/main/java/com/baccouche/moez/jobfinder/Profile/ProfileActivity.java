package com.baccouche.moez.jobfinder.Profile;

import android.app.ActivityGroup;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.baccouche.moez.jobfinder.Ads.MyApplicationsActivity;
import com.baccouche.moez.jobfinder.LoginActivity;
import com.baccouche.moez.jobfinder.R;

import java.io.File;

/**
 * Created by ASUS on 18/02/2018.
 */

public class ProfileActivity extends ActivityGroup {

    private ImageView im_user;

    private Button buttonMenuUser ;

    private TextView tvFullName;
    private TextView tvJob;





    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_full);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);


        tvFullName = (TextView) findViewById(R.id.tvFullName);
        tvJob = (TextView) findViewById(R.id.tvJob);
        im_user = (ImageView) findViewById(R.id.im_user);




        final String lastName = pref.getString("lastName", "");
        final String firstName = pref.getString("firstName", "");
        final String job = pref.getString("job", "");
        final String path = pref.getString("picture", "");

        String fullName = firstName + " " + lastName;



        tvJob.setText(job);
        tvFullName.setText(fullName);
        File imgFile = new File(path);
        if(imgFile.exists())
        {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            im_user.setImageBitmap(bitmap);
        }
        else
        {
            Log.v("ERROR IMAGE", "Image not found");
        }



        TabHost tab = (TabHost) findViewById(R.id.tabHost);
        tab.setup(getLocalActivityManager());
        TabHost.TabSpec spec1 = tab.newTabSpec("Coordonnées");
        spec1.setIndicator("Coordonnées");
        Intent intentDetails = new Intent(ProfileActivity.this, ProfileDetailsActivity.class);
        spec1.setContent(intentDetails);
        tab.addTab(spec1);

        TabHost.TabSpec spec2 = tab.newTabSpec("Détails personnels");
        spec2.setIndicator("Détails personnels");
        Intent intentPersonal = new Intent(ProfileActivity.this, PersonalDetailsActivity.class);
        spec2.setContent(intentPersonal);
        tab.addTab(spec2);



        buttonMenuUser = (Button) findViewById(R.id.buttonUserMenu);


        buttonMenuUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ProfileActivity.this, buttonMenuUser);
                popupMenu.getMenuInflater().inflate(R.menu.user_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Déconnexion"))
                        {
                            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(ProfileActivity.this, "Au revoir !", Toast.LENGTH_LONG).show();

                        }
                        else
                            if(item.getTitle().equals("Mes demandes"))
                            {
                                Intent intent = new Intent(ProfileActivity.this, MyApplicationsActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                                startActivity(intent);
                            }

                        return true;
                    }
                });

                popupMenu.show();
            }
        });

    }

}




