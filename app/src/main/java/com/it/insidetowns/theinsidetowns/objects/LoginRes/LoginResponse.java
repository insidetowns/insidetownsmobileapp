package com.it.insidetowns.theinsidetowns.objects.LoginRes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getUser_Pic() {
        return User_Pic;
    }

    public void setUser_Pic(String user_Pic) {
        User_Pic = user_Pic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public FMessage getfMessage() {
        return fMessage;
    }

    public void setfMessage(FMessage fMessage) {
        this.fMessage = fMessage;
    }

    @SerializedName("ID")
    @Expose
    private Integer ID;

    @SerializedName("User_Pic")
    @Expose
    private String User_Pic;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("phone")
    @Expose
    private String phone;



    @SerializedName("Message")
    @Expose
    private FMessage fMessage;


}
