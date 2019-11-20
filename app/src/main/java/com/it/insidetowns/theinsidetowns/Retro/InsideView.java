package com.it.insidetowns.theinsidetowns.Retro;

import retrofit2.Response;

public interface InsideView {
    void setSuccessResponse(Response response, int requestType);

    void setErrorResponse(String message, int requestType);
}