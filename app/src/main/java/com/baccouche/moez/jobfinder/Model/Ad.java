package com.baccouche.moez.jobfinder.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ad {

    @SerializedName("id_ad")
    @Expose
    private Integer idAd;
    @SerializedName("salary")
    @Expose
    private double salary;
    @SerializedName("difficulty")
    @Expose
    private String difficulty;
    @SerializedName("typeAd")
    @Expose
    private String typeAd;
    @SerializedName("field")
    @Expose
    private String field;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("pictures")
    @Expose
    private String pictures;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("idUser")
    @Expose
    private Integer idUser;
    @SerializedName("isValid")
    @Expose
    private Integer isValid;
    @SerializedName("dateLastEdit")
    @Expose
    private String dateLastEdit;
    @SerializedName("difficultyNumber")
    @Expose
    private Integer difficultyNumber;
    @SerializedName("payment")
    @Expose
    private String payment;


    public Ad(double salary, String difficulty, String typeAd, String field,
              String description, String pictures, String address, Integer idUser, Integer isValid, String dateLastEdit,
              Integer difficultyNumber, String payment)
    {
        this.salary = salary;
        this.difficulty = difficulty;
        this.typeAd = typeAd;
        this.field = field;
        this.description = description;
        this.pictures = pictures;
        this.address = address;
        this.idUser = idUser;
        this.isValid = isValid;
        this.dateLastEdit = dateLastEdit;
        this.difficultyNumber = difficultyNumber;
        this.payment = payment;
    }

    public Integer getIdAd() {
        return idAd;
    }



    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }


    public String getTypeAd() {
        return typeAd;
    }

    public void setTypeAd(String typeAd) {
        this.typeAd = typeAd;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getDateLastEdit() {
        return dateLastEdit;
    }

    public void setDateLastEdit(String dateLastEdit) {
        this.dateLastEdit = dateLastEdit;
    }

    public Integer getDifficultyNumber() {
        return difficultyNumber;
    }

    public void setDifficultyNumber(Integer difficultyNumber) {
        this.difficultyNumber = difficultyNumber;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}