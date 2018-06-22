package com.baccouche.moez.jobfinder;

import android.app.ActivityGroup;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TabHost;

import com.baccouche.moez.jobfinder.Ads.AdActivity;
import com.baccouche.moez.jobfinder.Ads.ApplicationsActivity;
import com.baccouche.moez.jobfinder.Ads.PlusAdActivityPart1;
import com.baccouche.moez.jobfinder.Ads.TabOwnerAdsActivity;
import com.baccouche.moez.jobfinder.Profile.ProfileActivity;

/**
 * Created by ASUS on 17/02/2018.
 */

public class HomeActivity extends ActivityGroup {

    private Button btMenuSort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



       // ProfileActivity profileActivity = new ProfileActivity();


        Resources res = getResources();




        TabHost tab = (TabHost) findViewById(R.id.tabHost);
        tab.setup(this.getLocalActivityManager());
        TabHost.TabSpec spec1 = tab.newTabSpec("ad");
        spec1.setIndicator("", res.getDrawable(R.drawable.doc));
        Intent intentAd = new Intent(HomeActivity.this, AdActivity.class);
        spec1.setContent(intentAd);
        tab.addTab(spec1);

        TabHost.TabSpec spec2 = tab.newTabSpec("acceptedApp");
        spec2.setIndicator("", res.getDrawable(R.drawable.check));
        Intent intentMyApplications = new Intent(HomeActivity.this, ApplicationsActivity.class);
        spec2.setContent(intentMyApplications);
        tab.addTab(spec2);


        TabHost.TabSpec spec3 = tab.newTabSpec("msg");
        spec3.setIndicator("", res.getDrawable(R.drawable.messages_32) );
        spec3.setContent(R.id.tab3_msg);
        tab.addTab(spec3);


        TabHost.TabSpec spec4 = tab.newTabSpec("profile");
        spec4.setIndicator("", res.getDrawable(R.drawable.use));
        Intent intentProfile = new Intent(HomeActivity.this, ProfileActivity.class);
        spec4.setContent(intentProfile);
        tab.addTab(spec4);

        TabHost.TabSpec spec5 = tab.newTabSpec("ownedAds");
        spec5.setIndicator("", res.getDrawable(R.drawable.add_ad32));
        Intent intentPlusAd = new Intent(HomeActivity.this, TabOwnerAdsActivity.class);
        spec5.setContent(intentPlusAd);
        tab.addTab(spec5);

        /*
        btMenuSort = (Button) findViewById(R.id.btMenuSort);

        btMenuSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(HomeActivity.this, btMenuSort);
                popupMenu.getMenuInflater().inflate(R.menu.app_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(HomeActivity.this, "Vous avez cliqué sur " + item.getTitle(), Toast.LENGTH_LONG).show();
                        return true;
                    }
                });

                popupMenu.show();
            }
        });

        */


    }


}
