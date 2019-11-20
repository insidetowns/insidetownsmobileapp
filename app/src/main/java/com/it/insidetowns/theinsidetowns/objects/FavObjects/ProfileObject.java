package com.it.insidetowns.theinsidetowns.objects.FavObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileObject
{


    @SerializedName("Email")
    @Expose
    private String Email;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getUser_Pic() {
        return User_Pic;
    }

    public void setUser_Pic(String user_Pic) {
        User_Pic = user_Pic;
    }

    public String getMobilenumber() {
        return Mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        Mobilenumber = mobilenumber;
    }

    @SerializedName("Firstname")
    @Expose
    private String Firstname;

    @SerializedName("User_Pic")
    @Expose
    private String User_Pic;

    @SerializedName("Mobilenumber")
    @Expose
    private String Mobilenumber;





}