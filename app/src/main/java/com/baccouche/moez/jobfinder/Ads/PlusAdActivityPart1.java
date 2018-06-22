package com.baccouche.moez.jobfinder.Ads;

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
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.baccouche.moez.jobfinder.R;
import com.baccouche.moez.jobfinder.Register.RegisterActivity;
import com.baccouche.moez.jobfinder.Register.RegisterBirthdateActivity;
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
 * Created by ASUS on 03/03/2018.
 */

public class PlusAdActivityPart1 extends AppCompatActivity {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    public static final int CAMERA_REQUEST = 1;

    public Uri imageUri;
    public File imageFile ;
    public String path = "/storage/emulated/0/DCIM/Camera/piclarge_launcher_foreground";
    public String checkPic = "/storage/emulated/0/DCIM/Camera/piclarge_launcher_foreground";

    private ImageView im_ad;
    private EditText etTitle;
    private EditText etDescription;
    private EditText etField;

    private String picture = "";
    private String imageName ="";
    public String description;

    private String directoryPath = "/storage/emulated/0/Pictures/";

    MultipartBody.Part multipartFile;

    private Button btNext;

    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus_ad_part_1);


    im_ad = (ImageView) findViewById(R.id.im_Ad);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etField = (EditText) findViewById(R.id.etField);
        btNext = (Button) findViewById(R.id.btNext);

        im_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final PopupMenu popupMenu = new PopupMenu(PlusAdActivityPart1.this, im_ad);
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





        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String title = etTitle.getText().toString();
                final String field = etField.getText().toString();
                description = etDescription.getText().toString();


                if(title.equals(""))
                {
                    Toast.makeText(PlusAdActivityPart1.this, "Veuillez ajouter un titre à votre annonce SVP !", Toast.LENGTH_SHORT).show();
                    etTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);
                }
                else
                    if(field.equals(""))
                    {
                        Toast.makeText(PlusAdActivityPart1.this, "Veuillez ajouter le domaine de l'annonce SVP !", Toast.LENGTH_SHORT).show();
                        etField.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);
                    }
                    else
                        if(description.equals(""))
                        {
                            Toast.makeText(PlusAdActivityPart1.this, "Veuillez ajouter la description de l'annonce SVP !", Toast.LENGTH_SHORT).show();
                            etDescription.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);
                        }
                        else
                            if(path.equals(checkPic))
                            {
                                Toast.makeText(PlusAdActivityPart1.this, "Veuillez choisir une photo pour votre annonce SVP !", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                etTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                                etDescription.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                                etField.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);

                                SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);


                                final int idUser = pref.getInt("idUser", 0);



                                IUser user = retrofit.create(IUser.class);
                                Call<String> call = user.uploadPhoto(multipartFile);
                                call.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {

                                        if(!imageName.equals(""))
                                        {
                                            picture = directoryPath + imageName;
                                        }
                                        else
                                        {
                                            picture = path;
                                        }

                                        Intent intent = new Intent(PlusAdActivityPart1.this, PlusAdActivityPart2.class);
                                        intent.putExtra("title", title);
                                        intent.putExtra("description", description);
                                        intent.putExtra("field", field);
                                        //intent.putExtra("path", path);
                                        intent.putExtra("idUser", idUser);
                                        intent.putExtra("pic", picture);
                                        //intent.putExtra("path", path);

                                        startActivity(intent);

                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Toast.makeText(PlusAdActivityPart1.this, "Erreuuuuur " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });




                            }
            }
        });


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
                    im_ad.setImageBitmap(bitmap);

                    //Added 10/03/2018
                    imageFile = new File("/storage/emulated/0/Pictures/" + imageName);
                    RequestBody filePart = RequestBody.create(MediaType.parse(getContentResolver().getType(selectedImage)), imageFile);
                    multipartFile = MultipartBody.Part.createFormData("photo", imageFile.getName(), filePart);
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else {
                if (requestCode == IMAGE_GALLERY_REQUEST) {
                    Uri imageUri = data.getData();
                    InputStream inputStream;
                    try {
                        inputStream = getContentResolver().openInputStream(imageUri);

                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        im_ad.setImageBitmap(bitmap);
                        imageFile = new File(getRealPathFromURI(imageUri));
                        path = imageFile.toString();


                        //Added 10/03/2018
                        RequestBody filePart = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), imageFile);
                        multipartFile = MultipartBody.Part.createFormData("photo", imageFile.getName(), filePart);

                    } catch (FileNotFoundException ex) {
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

        imageUri = FileProvider.getUriForFile(PlusAdActivityPart1.this, "com.baccouche.moez.jobfinder.provider", createImageFile());

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
