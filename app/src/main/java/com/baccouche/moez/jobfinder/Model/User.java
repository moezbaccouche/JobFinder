package com.baccouche.moez.jobfinder.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class User {

    @SerializedName("id_user")
    @Expose
    private Integer idUser;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("currentJob")
    @Expose
    private String currentJob;
    @SerializedName("degree")
    @Expose
    private String degree;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("birthdate")
    @Expose
    private String birthdate;


    @SerializedName("picture")
    @Expose
    private String picture;


    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;


    public User(String lastName, String firstName, String gender, String address, String email,
                String password, String currentJob, String degree, String status, String birthdate,
                String picture, String phoneNumber)
    {
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.password = password;
        this.currentJob = currentJob;
        this.degree = degree;
        this.status = status;
        this.birthdate = birthdate;
        this.picture = picture;
        this.phoneNumber = phoneNumber;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBirthDate() {
        return birthdate;
    }

    public void setBirthDate(String birthDate) {
        this.birthdate = birthdate;
    }



    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }



    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
