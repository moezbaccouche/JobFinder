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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.baccouche.moez.jobfinder.HomeActivity;
import com.baccouche.moez.jobfinder.Model.Ad;
import com.baccouche.moez.jobfinder.Model.User;
import com.baccouche.moez.jobfinder.Profile.EditProfileActivity;
import com.baccouche.moez.jobfinder.R;
import com.baccouche.moez.jobfinder.Retrofit.ConfigRetrofit;
import com.baccouche.moez.jobfinder.Retrofit.IUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ASUS on 17/03/2018.
 */

public class EditAdActivity extends Activity {

    private Button btReturn;
    private Button btSave;
    private Button btDelete;

    private ImageView imAd;

    private EditText etTitle;
    private EditText etDescription;
    private EditText etAddress;
    private EditText etField;
    private EditText etSalary;

    private Spinner spDifficulty;

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private RadioButton buttonDay;
    private RadioButton buttonHour;

    private String directoryPath = "/storage/emulated/0/Pictures/";

    public static final int IMAGE_GALLERY_REQUEST = 20;
    public static final int CAMERA_REQUEST = 1;

    public String picture;
    private String picturePath = "";

    private double salaryDouble;

    String path = "";

    public int difficultyNumber;

    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();

    private String imageName ="";
    private String name="";
    public Uri imageUri;
    public File imageFile ;
    MultipartBody.Part multipartFile;

    private int selectedId = -1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ad);

        btReturn = (Button) findViewById(R.id.btReturn);
        btSave = (Button) findViewById(R.id.btSave);
        btDelete = (Button) findViewById(R.id.btDelete);

        imAd = (ImageView) findViewById(R.id.imAd);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etField = (EditText) findViewById(R.id.etField);
        etSalary = (EditText) findViewById(R.id.etSalary);

        spDifficulty = (Spinner) findViewById(R.id.spDifficulty);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup2);
        buttonDay =  (RadioButton) findViewById(R.id.buttonDay);
        buttonHour =  (RadioButton) findViewById(R.id.buttonHour);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("adInfo", 0);

        final int idAd = pref.getInt("idAd", 0);

        SharedPreferences prefUser = getApplicationContext().getSharedPreferences("userInfo", 0);
        final int idUser = prefUser.getInt("idUser", 0);





        IUser user = retrofit.create(IUser.class);
        Call<Ad> call = user.getAdById(idAd);

        call.enqueue(new Callback<Ad>() {
            @Override
            public void onResponse(Call<Ad> call, Response<Ad> response) {
                String description = response.body().getDescription();
                double salary = response.body().getSalary();
                String field = response.body().getField();
                String address = response.body().getAddress();
                String title = response.body().getTypeAd();
                String payment = response.body().getPayment();
                int difficultyNumber = response.body().getDifficultyNumber();

                picturePath = response.body().getPictures();

                File imgFile = new File(picturePath);
                if(imgFile.exists())
                {
                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imAd.setImageBitmap(bitmap);
                }
                else
                {
                    Log.v("IMAGE NOT FOUND", "IMAGE NOT FOUND");
                }


                etAddress.setText(address);
                etDescription.setText(description);
                etSalary.setText(String.valueOf(salary));
                etField.setText(field);
                etTitle.setText(title);

                int position = 0;

                switch(difficultyNumber)
                {
                    case 1 : position = 0; break;
                    case 2 : position = 1; break;
                    case 3 : position = 2; break;
                }

                spDifficulty.setSelection(position);

                if(payment.equals("Par jour"))
                {
                    buttonDay.setChecked(true);
                    buttonHour.setChecked(false);
                }
                else
                    if(payment.equals("Par heure"))
                    {
                        buttonDay.setChecked(false);
                        buttonHour.setChecked(true);
                    }

            }

            @Override
            public void onFailure(Call<Ad> call, Throwable t) {
                Toast.makeText(EditAdActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAd(idAd, idUser);
            }
        });






        imAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final PopupMenu popupMenu = new PopupMenu(EditAdActivity.this, imAd);
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


        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAd(idAd);
            }
        });

        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAdActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });



    }

    public void editAd(final int idAd, final int idUser)
    {
        String address = etAddress.getText().toString();
        String description = etDescription.getText().toString();
        String salary = etSalary.getText().toString();
        String difficulty = spDifficulty.getSelectedItem().toString();
        String title = etTitle.getText().toString();
        String field = etField.getText().toString();

        selectedId = radioGroup.getCheckedRadioButtonId();

        radioButton = (RadioButton) findViewById(selectedId);

        String payment = radioButton.getText().toString();

        if(difficulty.equals("Facile"))
        {
            difficultyNumber = 1;
        }
        else
        if(difficulty.equals("Moyenne"))
        {
            difficultyNumber = 2;
        }
        else
        if(difficulty.equals("Difficile"))
        {
            difficultyNumber = 3;
        }


        if(!imageName.equals(""))
        {
            picture = directoryPath + imageName;
        }
        else
        {
            picture = path;
        }

        if(address.equals("") || description.equals("") || payment.equals("") || difficulty.equals("") || field.equals("") || title.equals("") || salary.equals(""))
        {
            Toast.makeText(this, "Veuillez remplir tous les champs SVP !", Toast.LENGTH_SHORT).show();
        }
        else
        if(picture.equals(""))
        {
            Toast.makeText(this, "Veuillez choisir une photo SVP", Toast.LENGTH_SHORT).show();
        }
        else
        {
            try
            {
                salaryDouble = Double.parseDouble(salary);
            }
            catch(Exception e)
            {
                Toast.makeText(this, "Veuillez saisir un salaire correct SVP !", Toast.LENGTH_SHORT).show();
            }

            String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());

            String dateLastEdit = timeStamp;

            Ad ad = new Ad(
                    salaryDouble,
                    difficulty,
                    title,
                    field,
                    description,
                    picture,
                    address,
                    idUser,
                    1,
                    dateLastEdit,
                    difficultyNumber,
                    payment
            );

            IUser user = retrofit.create(IUser.class);
            Call<Ad> call = user.updateAd(idAd, ad);
            call.enqueue(new Callback<Ad>() {
                @Override
                public void onResponse(Call<Ad> call, Response<Ad> response) {

                    Toast.makeText(EditAdActivity.this, "Mise à jour de l'annonce effectuée !", Toast.LENGTH_LONG).show();

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("adInfo", 0);

                    SharedPreferences.Editor editor = pref.edit();

                    editor.putInt("idAd", idAd);

                    editor.commit();

                    startActivity(new Intent(EditAdActivity.this, HomeActivity.class));

                }

                @Override
                public void onFailure(Call<Ad> call, Throwable t) {
                    Toast.makeText(EditAdActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private void deleteAd(int idAd)
    {
        IUser ad = retrofit.create(IUser.class);
        Call<Ad> call = ad.deleteAd(idAd);
        call.enqueue(new Callback<Ad>() {
            @Override
            public void onResponse(Call<Ad> call, Response<Ad> response) {
                if(response.body() != null)
                {
                    Toast.makeText(EditAdActivity.this, "Supression de l'annonce effectuée avec succés !", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EditAdActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Ad> call, Throwable t) {
                Toast.makeText(EditAdActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    imAd.setImageBitmap(bitmap);

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
                        imAd.setImageBitmap(bitmap);
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

        imageUri = FileProvider.getUriForFile(EditAdActivity.this, "com.baccouche.moez.jobfinder.provider", createImageFile());

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
