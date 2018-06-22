package com.baccouche.moez.jobfinder.Ads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.baccouche.moez.jobfinder.Retrofit.ConfigRetrofit;
import com.baccouche.moez.jobfinder.Retrofit.IUser;
import com.baccouche.moez.jobfinder.Model.Ad;
import com.baccouche.moez.jobfinder.R;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ASUS on 19/02/2018.
 */

public class AdActivity extends Activity {

    private ListView listView;
    private List<Ad> listAds;
    private Button btMenuSort;
    private Call<List<Ad>> call;
    private String choiceSort = "";
    private String choiceTypeSort = "";
    private EditText etSearch;
    private Context context;




    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();


    public String title = "";


    private List<Ad> ads;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ads);

        listView = (ListView) findViewById(R.id.listAds);
        btMenuSort = (Button) findViewById(R.id.btMenuSort);
        etSearch = (EditText) findViewById(R.id.etSearch);




        btMenuSort = (Button) findViewById(R.id.btMenuSort);

        btMenuSort.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               PopupMenu popupMenu = new PopupMenu(AdActivity.this, btMenuSort);
               popupMenu.getMenuInflater().inflate(R.menu.app_menu, popupMenu.getMenu());
               popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   @Override
                   public boolean onMenuItemClick(MenuItem item) {

                       title = item.getTitle().toString();
                       Toast.makeText(AdActivity.this, "Annonces triées selon : " + title, Toast.LENGTH_LONG).show();


                       if(title.equals("Salaire descendant"))
                       {
                           choiceSort = "salary";
                           choiceTypeSort = "DESC";
                       }
                       else
                        if(title.equals("Difficulté ascendante"))
                        {
                           choiceSort = "difficultyNumber";
                           choiceTypeSort = "ASC";
                        }

                       IUser ad = retrofit.create(IUser.class);
                       call = ad.getAdsSortedBy(choiceSort, choiceTypeSort);
                       call.enqueue(new Callback<List<Ad>>() {
                           @Override
                           public void onResponse(Call<List<Ad>> call, Response<List<Ad>> response) {
                               listAds = (List<Ad>) response.body();
                               listView.setAdapter(new AdListAdapter(getApplicationContext(), listAds));

                               Toast.makeText(AdActivity.this, "Annonces triées selon : " + title, Toast.LENGTH_SHORT).show();

                           }

                           @Override
                           public void onFailure(Call<List<Ad>> call, Throwable t) {
                               Toast.makeText(AdActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       });

                       return true;
                   }
               });

               popupMenu.show();
           }
       });

        callAdsList();


        etSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    final String keyWord = etSearch.getText().toString();
                    IUser ad = retrofit.create(IUser.class);
                    if (!keyWord.equals("")) {
                        Call<List<Ad>> call = ad.getAdsByKeyWord(keyWord);
                        call.enqueue(new Callback<List<Ad>>() {
                            @Override
                            public void onResponse(Call<List<Ad>> call, Response<List<Ad>> response) {

                                    listAds = (List<Ad>) response.body();
                                    if(listAds.size() != 0)
                                    {
                                        listView.setAdapter(new AdListAdapter(getApplicationContext(), listAds));

                                        Toast.makeText(AdActivity.this, "Recherche par le mot clé : " + keyWord, Toast.LENGTH_SHORT).show();


                                    }
                                    else
                                    {
                                        Toast.makeText(AdActivity.this, "Aucun emploi ne correspond au mot clé saisi, veuillez réessayer", Toast.LENGTH_LONG).show();
                                        callAdsList();
                                    }
                            }

                            @Override
                            public void onFailure(Call<List<Ad>> call, Throwable t) {
                                Toast.makeText(AdActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else
                    {
                        callAdsList();
                    }

                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    return true;
                }
                return  false;

            }


        });
    }







    private void callAdsList() {


        IUser iuser = retrofit.create(IUser.class);

        Call call = iuser.getAds();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                listAds = (List<Ad>) response.body();
                listView.setAdapter(new AdListAdapter(getApplicationContext(), listAds));

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        final int idAd = listAds.get(position).getIdAd();


                        Intent intent = new Intent(AdActivity.this, AdDetails.class);

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
                Toast.makeText(AdActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}




