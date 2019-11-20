package com.it.insidetowns.theinsidetowns.Retro;

import com.google.gson.GsonBuilder;
import com.it.insidetowns.theinsidetowns.objects.LoginRes.LoginResponse;

import retrofit2.Response;

public class BasePresenter {

    public void loginerrorBody(Response response, InsideView insideView, int requestType) {

        try {
            LoginResponse msgError = new GsonBuilder().create().fromJson(response.errorBody().string(), LoginResponse.class);
            insideView.setErrorResponse(msgError.getfMessage().getMessage(), requestType);
        } catch (Exception e) {
            insideView.setErrorResponse(response.message(), requestType);
            e.printStackTrace();
        }

    }
}
