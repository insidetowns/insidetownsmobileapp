package com.it.insidetowns.theinsidetowns.objects.ProfileUpdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileCredentials
{


    public Integer getUserid() {
        return Userid;
    }

    public void setUserid(Integer userid) {
        Userid = userid;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfile_Pic() {
        return Profile_Pic;
    }

    public void setProfile_Pic(String profile_Pic) {
        Profile_Pic = profile_Pic;
    }

    @SerializedName("Userid")
    @Expose
    private Integer Userid;
    @SerializedName("Firstname")
    @Expose
    private String Firstname;
    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("Profile_Pic")
    @Expose
    private String Profile_Pic;



}