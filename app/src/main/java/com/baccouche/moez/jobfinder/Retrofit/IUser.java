package com.baccouche.moez.jobfinder.Retrofit;

import com.baccouche.moez.jobfinder.Model.Ad;
import com.baccouche.moez.jobfinder.Model.Application;
import com.baccouche.moez.jobfinder.Model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface IUser {

    @GET("/users/getUsers")
    Call<List<User>> getAllUsers();


    @GET("/users/getUserById/{email}/{password}")
    Call<User> getUser(@Path("email") String email, @Path("password") String pw);


    @GET("/users/getUserByEmail/{email}")
    Call<User> getUserByEmail(@Path("email") String email);

    @POST("users/addUser")
    Call<User> addUser(@Body User user);


    @GET("ads/getAds")
    Call<List<Ad>> getAds();

    @GET("ads/getAdById/{id}")
    Call<Ad> getAdById(@Path("id") int id);

    @GET("users/getOwnerById/{id}")
    Call<User> getOwnerById(@Path("id") int id);

    @GET("ads/getAdsSortedBy/{sort}/{typeSort}")
    Call<List<Ad>> getAdsSortedBy(@Path("sort") String sort, @Path("typeSort") String typeSort);

    @GET("ads/getAdsByKeyWord/{keyWord}")
    Call<List<Ad>> getAdsByKeyWord(@Path("keyWord") String keyWord);

    @GET("users/getOwner/{id}")
    Call<User> getOwner(@Path("id") Integer id);

    @POST("ads/plusAd")
    Call<Ad> plusAd(@Body Ad ad);

    @Multipart
    @POST("uploads/upload")
    Call<String> uploadPhoto(@Part MultipartBody.Part photo);


    @POST("applications/addApplication")
    Call<Application> addApplication(@Body Application application);

    @GET("applications/getApplication/{id}")
    Call<Application> getApplication(@Path("idOwner") int idOwner);

    @GET("applications/getApplicationsByOwnerId/{idOwner}")
    Call<List<Application>> getApplicationsByOwnerId(@Path("idOwner") int idOwner);

    @GET("applications/checkApplication/{idUser}/{idAd}")
    Call<Application> checkApplication(@Path("idUser") int idUser, @Path("idAd") int idAd);


    @GET("applications/acceptApplication/{idUser}/{idAd}")
    Call<Application> acceptApplication(@Path("idUser") int idUser, @Path("idAd") int idAd);

    @GET("applications/rejectApplication/{idUser}/{idAd}")
    Call<Application> rejectApplication(@Path("idUser") int idUser, @Path("idAd") int idAd);

    @GET("ads/getAdsByOwnerId/{idOwner}")
    Call<List<Ad>> getAdsByOwnerId(@Path("idOwner") int idOwner);


    @GET("applications/getApplicationsByUserId/{idUser}")
    Call<List<Application>> getApplicationsByUserId(@Path("idUser") int idUser);


    @POST("users/updateUser/{idUser}")
    Call<User> updateUser(@Path("idUser") int idUser, @Body User user);


    @POST("ads/updateAd/{idAd}")
    Call<Ad> updateAd(@Path("idAd") int idAd, @Body Ad ad);


    @GET("ads/getNewAds/{idAd}")
    Call<Ad> getNewAds(@Path("idAd") int idAd);


    @GET("ads/getMaxIdAd")
    Call<String> getMaxIdAd();


    @GET("ads/deleteAd/{idAd}")
    Call<Ad> deleteAd(@Path("idAd") int idAd);

}
