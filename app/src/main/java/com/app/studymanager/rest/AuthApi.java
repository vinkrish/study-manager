package com.app.studymanager.rest;

import com.app.studymanager.models.Credentials;
import com.app.studymanager.models.CommonResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Vinay on 24-10-2016.
 */

public interface AuthApi {
    @POST("user/signup")
    Call<CommonResponse> signup(@Body HashMap<String,String> body);

    @Headers("content-type: application/json")
    @POST("user/login")
    Call<Credentials> login(@Body HashMap<String,String> body);

}
