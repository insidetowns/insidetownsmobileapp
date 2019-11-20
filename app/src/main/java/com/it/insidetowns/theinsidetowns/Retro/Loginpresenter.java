package com.it.insidetowns.theinsidetowns.Retro;

import com.it.insidetowns.theinsidetowns.objects.LoginRes.LoginCredentials;
import com.it.insidetowns.theinsidetowns.objects.LoginRes.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Loginpresenter extends BasePresenter{
    private InsideView insideView;
    private InsideToenService insideToenService;

    public Loginpresenter(InsideView insideView) {
        this.insideView = insideView;
        if (this.insideToenService == null) {
            this.insideToenService = new InsideToenService();
        }
    }

    public void getLogin(LoginCredentials loginModel, final int requestType) {
        insideToenService
                .getAPI()
                .postLogin(loginModel)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.body() != null) {
                            insideView.setSuccessResponse(response, requestType);
                        } else if (response.errorBody() != null) {
                            loginerrorBody(response,insideView, requestType);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        insideView.setErrorResponse("Something went wrong", requestType);
                    }
                });
    }



}
