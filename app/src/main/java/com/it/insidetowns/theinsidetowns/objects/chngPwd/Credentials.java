package com.it.insidetowns.theinsidetowns.objects.chngPwd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Credentials
{

    public Integer getUserid() {
        return Userid;
    }

    public void setUserid(Integer userid) {
        Userid = userid;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String oldPassword) {
        OldPassword = oldPassword;
    }

    @SerializedName("Userid")
    @Expose
    private Integer Userid;
    @SerializedName("Password")
    @Expose
    private String Password;
    @SerializedName("OldPassword")
    @Expose
    private String OldPassword;



}