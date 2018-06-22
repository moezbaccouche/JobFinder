package com.baccouche.moez.jobfinder.Profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.baccouche.moez.jobfinder.HomeActivity;
import com.baccouche.moez.jobfinder.Model.User;
import com.baccouche.moez.jobfinder.R;
import com.baccouche.moez.jobfinder.Register.RegisterActivity;
import com.baccouche.moez.jobfinder.Retrofit.ConfigRetrofit;
import com.baccouche.moez.jobfinder.Retrofit.IUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ASUS on 11/03/2018.
 */

public class EditProfileActivity extends Activity {

    private Button btSave;
    private Button btReturn;

    private ImageView imUser;

    private EditText etAddress;
    private EditText etLastName;
    private EditText etFirstName;
    private EditText etBirthDate;
    private EditText etPhoneNumber;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etCurrentJob;
    private EditText etDegree;
    private EditText etStatus;

    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();

    private String imageName ="";
    private String name="";
    public Uri imageUri;
    public File imageFile ;
    MultipartBody.Part multipartFile;

    public String picture;
    private String picturePath = "";

    String path = "";
    public String gender = "";

    private String directoryPath = "/storage/emulated/0/Pictures/";

    public static final int IMAGE_GALLERY_REQUEST = 20;
    public static final int CAMERA_REQUEST = 1;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);

        final int ID = pref.getInt("idUser", 0);
        gender = pref.getString("gender","");

        etAddress = (EditText) findViewById(R.id.etAddress);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etBirthDate = (EditText) findViewById(R.id.etBirthDate);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etCurrentJob = (EditText) findViewById(R.id.etCurrentJob);
        etDegree = (EditText) findViewById(R.id.etDegree);
        etStatus = (EditText) findViewById(R.id.etStatus);

        btSave = (Button) findViewById(R.id.btSave);
        btReturn = (Button) findViewById(R.id.btReturn);

        imUser = (ImageView) findViewById(R.id.imUser);

        IUser user = retrofit.create(IUser.class);
        Call<User> call = user.getOwner(ID);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                String lastName = response.body().getLastName();
                String firstName = response.body().getFirstName();
                String birthDate = response.body().getBirthDate();
                String address = response.body().getAddress();
                String phoneNumber = response.body().getPhoneNumber();
                String email = response.body().getEmail();
                String degree = response.body().getDegree();
                String currentJob = response.body().getCurrentJob();
                String status = response.body().getStatus();
                String password = response.body().getPassword();
                picturePath = response.body().getPicture();

                File imgFile = new File(picturePath);
                if(imgFile.exists())
                {
                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imUser.setImageBitmap(bitmap);
                }
                else
                {
                    Log.v("IMAGE NOT FOUND", "IMAGE NOT FOUND");
                }

/*
                if(!imageName.equals(""))
                {
                    picture = directoryPath + imageName;
                }
                else
                {
                    picture = pathPath;
                }
*/

                etAddress.setText(address);
                etLastName.setText(lastName);
                etFirstName.setText(firstName);
                etBirthDate.setText(birthDate);
                etPhoneNumber.setText(phoneNumber);
                etEmail.setText(email);
                etDegree.setText(degree);
                etCurrentJob.setText(currentJob);
                etStatus.setText(status);
                etPassword.setText(password);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        imUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final PopupMenu popupMenu = new PopupMenu(EditProfileActivity.this, imUser);
                popupMenu.getMenuInflater().inflate(R.menu.picture_menu, popupMenu.getMenu());




                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        //String path = Environment.getExternalStorageDirectory().getPath();

                        if(item.getTitle().equals("Prendre une photo"))
                        {
                            takePhoto();
                            return true;
                        }
                        else
                        {
                            Intent intent = new Intent(Intent.ACTION_PICK);

                            File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                            String pictureDirectoryPath = pictureDirectory.getPath();

                            Uri data = Uri.parse(pictureDirectoryPath);

                            intent.setDataAndType(data, "image/*");


                            startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
                            return true;

                        }


                    }
                });

                popupMenu.show();
            }
        });


        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile(ID);
            }
        });

        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this, HomeActivity.class));
            }
        });
    }


    public void editProfile(final int id)
    {
        String address = etAddress.getText().toString();
        String lastName = etLastName.getText().toString();
        String firstName = etFirstName.getText().toString();
        String birthDate = etBirthDate.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        String email = etEmail.getText().toString();
        String degree = etDegree.getText().toString();
        String currentJob = etCurrentJob.getText().toString();
        String status =  etStatus.getText().toString();
        String password = etPassword.getText().toString();


        if(!imageName.equals(""))
        {
            picture = directoryPath + imageName;
        }
        else
        {
            picture = path;
        }

        if(address.equals("") || lastName.equals("") || firstName.equals("") || birthDate.equals("") || phoneNumber.equals("") || email.equals("") || degree.equals("") || password.equals("") || currentJob.equals("") || status.equals(""))
        {
            Toast.makeText(this, "Veuillez remplir tous les champs SVP !", Toast.LENGTH_SHORT).show();
        }
        else
            if(password.length() < 8)
            {
                Toast.makeText(this, "Le mot de passe doit contenir au moins 8 caractères !", Toast.LENGTH_SHORT).show();
            }
            else
                if(picture.equals(""))
                {
                    Toast.makeText(this, "Veuillez choisir une photo SVP", Toast.LENGTH_SHORT).show();
                }
            else
            {
                User client = new User(
                        lastName,
                        firstName,
                        gender,
                        address,
                        email,
                        password,
                        currentJob,
                        degree,
                        status,
                        birthDate,
                        picture,
                        phoneNumber
                );

                IUser user = retrofit.create(IUser.class);
                Call<User> call = user.updateUser(id, client);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                            Toast.makeText(EditProfileActivity.this, "Mise à jour du profil effectuée !", Toast.LENGTH_LONG).show();

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
                            editor.putInt("idUser", id);

                            editor.commit();

                            startActivity(new Intent(EditProfileActivity.this, HomeActivity.class));

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(EditProfileActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }


    }


    private String getPictureName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timeStamp = sdf.format(new Date());
        return "Picture" + timeStamp + ".jpg";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Uri selectedImage = imageUri;
                getContentResolver().notifyChange(selectedImage, null);
                ContentResolver cr = getContentResolver();
                Bitmap bitmap;
                path = selectedImage.toString();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                    imUser.setImageBitmap(bitmap);

                    //Added 10/03/2018
                    imageFile = new File(directoryPath + imageName);
                    RequestBody filePart = RequestBody.create(MediaType.parse(getContentResolver().getType(selectedImage)), imageFile);
                    multipartFile = MultipartBody.Part.createFormData("photo", imageFile.getName(), filePart);

                } catch (Exception e) {
                    Toast.makeText(this,"Error here : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else {
                if (requestCode == IMAGE_GALLERY_REQUEST) {
                    Uri imageUri = data.getData();
                    InputStream inputStream;
                    try {
                        inputStream = getContentResolver().openInputStream(imageUri);

                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imUser.setImageBitmap(bitmap);
                        imageFile = new File(getRealPathFromURI(imageUri));
                        path = imageFile.toString();


                        //Added 10/03/2018
                        RequestBody filePart = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), imageFile);
                        multipartFile = MultipartBody.Part.createFormData("photo", imageFile.getName(), filePart);

                    }
                    catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                        Toast.makeText(this, "Impossible d'ouvrir l'image", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }
    }


    private String getRealPathFromURI(Uri contentURI)
    {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if(cursor == null)
        {
            result = contentURI.getPath();
        }
        else
        {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }

    private void takePhoto()
    {

        imageUri = FileProvider.getUriForFile(EditProfileActivity.this, "com.baccouche.moez.jobfinder.provider", createImageFile());

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        startActivityForResult(intent, CAMERA_REQUEST);

    }

    private File createImageFile()
    {

        File picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        imageName = getPictureName();
        File photo = new File(picturesDirectory, imageName);

        return photo;
    }
}
