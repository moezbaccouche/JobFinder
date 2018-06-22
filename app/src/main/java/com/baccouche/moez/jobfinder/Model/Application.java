package com.baccouche.moez.jobfinder.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ASUS on 10/03/2018.
 */

public class Application {

    @SerializedName("id_application")
    @Expose
    private Integer idApplication;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("id_user")
    @Expose
    private Integer idUser;
    @SerializedName("id_ad")
    @Expose
    private Integer idAd;
    @SerializedName("idOwner")
    @Expose
    private Integer idOwner;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("typeAd")
    @Expose
    private String typeAd;
    @SerializedName("dateApplication")
    @Expose
    private String dateApplication;
    @SerializedName("pictures")
    @Expose
    private String pictures;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("salary")
    @Expose
    private double salary;
    @SerializedName("payment")
    @Expose
    private String payment;


    public Application(String note, String status, Integer idUser, Integer idAd, Integer idOwner, String dateApplication) {
        this.note = note;
        this.status = status;
        this.idUser = idUser;
        this.idAd = idAd;
        this.idOwner = idOwner;
        this.dateApplication = dateApplication;
    }


    public Integer getIdApplication() {
        return idApplication;
    }

    public String getNote() {
        return note;
    }


    public Integer getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(Integer idOwner) {
        this.idOwner = idOwner;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdAd() {
        return idAd;
    }

    public void setIdAd(Integer idAd) {
        this.idAd = idAd;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public String getTypeAd() {
        return typeAd;
    }

    public void setTypeAd(String typeAd) {
        this.typeAd = typeAd;
    }

    public String getDateApplication() {
        return dateApplication;
    }

    public void setDateApplication(String dateApplication) {
        this.dateApplication = dateApplication;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
