package com.baccouche.moez.jobfinder.Register;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.baccouche.moez.jobfinder.R;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ASUS on 17/02/2018.
 */

public class RegisterActivity extends Activity {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    public static final int CAMERA_REQUEST = 1;

    public static final int CAMERA_PERMISSION_REQUEST_CODE = 4192;
    private ImageView im_user;
    private EditText et_Name;
    private EditText et_firstName;
    private Button btNext;
    private String imageName ="";
    private String name="";
    public Uri imageUri;
    public File imageFile ;
    public String checkPic = "/storage/emulated/0/DCIM/Camera/piclarge_launcher_foreground";
    public String path = "/storage/emulated/0/DCIM/Camera/piclarge_launcher_foreground";

    private String directoryPath = "/storage/emulated/0/Pictures/";

    private String picture = "";

    MultipartBody.Part multipartFile;

    public static final int LOAD_IMAGE_RESULTS = 1;

    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_Name = (EditText) findViewById(R.id.etLastName);
        et_firstName = (EditText) findViewById(R.id.etFirstName);
        btNext = (Button) findViewById(R.id.btNext);
        im_user = (ImageView) findViewById(R.id.picUser);

        name = et_Name.getText().toString();



        im_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final PopupMenu popupMenu = new PopupMenu(RegisterActivity.this, im_user);
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

                final String name = et_Name.getText().toString();
                final String firstName = et_firstName.getText().toString();

                if(!firstName.equals(""))
                {
                    et_firstName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                }

                if(!name.equals(""))
                {
                    et_Name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                }


                if(name.equals(""))
                {
                    et_Name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);
                    Toast.makeText(RegisterActivity.this, "Saisissez votre nom SVP !", Toast.LENGTH_LONG).show();
                }
                else
                    if(firstName.equals(""))
                    {
                        et_firstName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);
                        Toast.makeText(RegisterActivity.this, "Saisissez votre prénom SVP !", Toast.LENGTH_LONG).show();
                    } 
                else
                     if(name.equals("") && firstName.equals(""))
                     {
                         et_Name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);
                         et_firstName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.incorrect32, 0);
                         Toast.makeText(RegisterActivity.this, "Veuillez saisir votre nom et prénom SVP !", Toast.LENGTH_SHORT).show();
                     }
                else
                    if(path.equals(checkPic))
                    {
                        Toast.makeText(RegisterActivity.this, "Veuillez choisir une photo SVP !", Toast.LENGTH_LONG).show();
                    }
                else
                {

                    et_Name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);
                    et_firstName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid32, 0);



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

                            Intent intent = new Intent(RegisterActivity.this, RegisterBirthdateActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("firstName", firstName);
                            intent.putExtra("pic", picture);
                            //intent.putExtra("path", path);

                            startActivity(intent);

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Erreuuuuur " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    im_user.setImageBitmap(bitmap);

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
                        im_user.setImageBitmap(bitmap);
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

        imageUri = FileProvider.getUriForFile(RegisterActivity.this, "com.baccouche.moez.jobfinder.provider", createImageFile());

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