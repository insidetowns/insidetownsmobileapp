package com.it.insidetowns.theinsidetowns.objects.LoginRes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginCredentials implements Serializable {

    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("Social_LoginId")
    @Expose
    private String socialLoginId;

    public String getFbLoginId() {
        return fbLoginId;
    }

    public void setFbLoginId(String fbLoginId) {
        this.fbLoginId = fbLoginId;
    }

    @SerializedName("fb_LoginId")
    @Expose
    private String fbLoginId;
    @SerializedName("IsSocial")
    @Expose
    private boolean isSocial;
    @SerializedName("Social_LoginType")
    @Expose
    private String socialLoginType;

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

    public String getSocialLoginId() {
        return socialLoginId;
    }

    public void setSocialLoginId(String socialLoginId) {
        this.socialLoginId = socialLoginId;
    }

    public boolean getIsSocial() {
        return isSocial;
    }

    public void setIsSocial(boolean isSocial) {
        this.isSocial = isSocial;
    }

    public String getSocialLoginType() {
        return socialLoginType;
    }

    public void setSocialLoginType(String socialLoginType) {
        this.socialLoginType = socialLoginType;
    }

}
