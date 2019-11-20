package com.it.insidetowns.theinsidetowns.Retro;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ganesh on 5/23/2018.
 */

public interface ApiService {
    @GET("api/Common/FavCategories")
    Call<LoginResponseRetro> login(@Query("Userid") String username);
}
