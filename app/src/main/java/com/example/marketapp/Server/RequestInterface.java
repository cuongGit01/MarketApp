package com.example.marketapp.Server;

import com.example.marketapp.Server.ServerRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("login/login.php")
    Call<ServerResponse> loginInterface (@Body ServerRequest request);
    @POST("login/register.php")
    Call<ServerResponse> registerInterface (@Body ServerRequest request);
}
