package com.it.insidetowns.theinsidetowns.Retro;


import com.it.insidetowns.theinsidetowns.objects.LoginRes.LoginCredentials;
import com.it.insidetowns.theinsidetowns.objects.LoginRes.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InsideAPI {
    @POST("user/Login")
    Call<LoginResponse> postLogin(@Body LoginCredentials loginModel);
}
