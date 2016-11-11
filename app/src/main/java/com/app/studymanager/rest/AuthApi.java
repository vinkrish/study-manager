package com.app.studymanager.rest;

import com.app.studymanager.models.Credentials;
import com.app.studymanager.models.CommonResponse;
import com.app.studymanager.models.Profile;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
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

    @Headers("content-type: application/json")
    @POST("user/sendNewPassword")
    Call<CommonResponse> newPassword(@Body HashMap<String,String> body);

    @GET("api/getUserProfile")
    Call<Profile> getUserProfile(@HeaderMap Map<String,String> headers);

    @Headers("content-type: application/json")
    @POST("api/updateUserProfile")
    Call<CommonResponse> updateUserProfile(@HeaderMap Map<String,String> headers,
                                           @Body HashMap<String,String> body);

    @Headers("content-type: application/json")
    @POST("api/resetPassword")
    Call<CommonResponse> resetPassword(@HeaderMap Map<String,String> headers,
                                       @Body HashMap<String,String> body);

}
