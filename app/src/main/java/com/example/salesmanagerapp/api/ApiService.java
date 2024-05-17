package com.example.salesmanagerapp.api;

import com.example.salesmanagerapp.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/avLogin/Get")
    Call<LoginResponse> loginUser(
            @Query("id") String id,
            @Query("password") String password,
            @Query("macaddress") String macaddress,
            @Query("versionnumber") String versionnumber,
            @Query("deviceid") String deviceid);
}
