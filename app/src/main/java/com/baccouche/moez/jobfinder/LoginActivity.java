package com.baccouche.moez.jobfinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baccouche.moez.jobfinder.Model.User;
import com.baccouche.moez.jobfinder.Register.RegisterActivity;
import com.baccouche.moez.jobfinder.Retrofit.ConfigRetrofit;
import com.baccouche.moez.jobfinder.Retrofit.IUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    //final String EXTRA_EMAIL = "user_email";

    private Button btConnect;
    private Button btRegister;

    private EditText etLogin ;
    private EditText etPw;

    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btConnect = (Button) findViewById(R.id.btConnect);
        btRegister = (Button) findViewById(R.id.btRegister);
        etLogin = (EditText) findViewById(R.id.etMail);
        etPw = (EditText) findViewById(R.id.etPw);

        login();
        register();
    }


    private void login()
    {
      btConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String login = etLogin.getText().toString();
                String password = etPw.getText().toString();


                if(!login.equals("") && !password.equals(""))
                {
                    IUser user = retrofit.create(IUser.class);
                    Call<User> call = user.getUser(login, password);

                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                            if(response.body().getLastName() != null)
                            {
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);


                                SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("lastName", response.body().getLastName());
                                editor.putString("firstName", response.body().getFirstName());
                                editor.putString("gender", response.body().getGender());
                                editor.putString("email", response.body().getEmail());
                                editor.putString("job", response.body().getCurrentJob());
                                editor.putString("picture", response.body().getPicture());
                                editor.putString("address", response.body().getAddress());
                                editor.putString("phone", response.body().getPhoneNumber());
                                editor.putString("degree", response.body().getDegree());
                                editor.putString("birthdate", response.body().getBirthDate());
                                editor.putString("status", response.body().getStatus());
                                editor.putInt("idUser", response.body().getIdUser());

                                editor.commit();

                                startActivity(intent);

                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "Email ou mot de passe incorrect !", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Erreur : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Saisissez votre mot de passe ! ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void register()
    {
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}
