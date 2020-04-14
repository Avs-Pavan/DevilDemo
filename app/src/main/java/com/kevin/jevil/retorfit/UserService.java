package com.kevin.jevil.retorfit;

import com.kevin.jevil.retorfit.models.MResponce;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {
    @GET("/api/users?page=2")
    Call<MResponce> fail();

    @GET("/api/users/2")
    Call<MResponce> getUser();

    @GET("/api/users/23")
    Call<MResponce> get404();

}
