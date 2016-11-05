package com.app.studymanager.login;

import android.text.TextUtils;

import com.app.studymanager.models.Credentials;
import com.app.studymanager.rest.ApiClient;
import com.app.studymanager.rest.AuthApi;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 23-10-2016.
 */

public class LoginInteractorImpl implements LoginInteractor {
    @Override
    public void login(String email, String password, final OnLoginFinishedListener listener) {

        AuthApi authApi = ApiClient.getClient().create(AuthApi.class);

        HashMap<String,String> body = new HashMap<String, String>();
        body.put("email", email);
        body.put("password", password);

        Call<Credentials> login = authApi.login(body);
        login.enqueue(new Callback<Credentials>() {
            @Override
            public void onResponse(Call<Credentials> call, Response<Credentials> response) {
                if(response.body().isSuccess()){
                    listener.onSuccess(response.body());
                } else {
                    listener.onLoginFailed();
                }
            }

            @Override
            public void onFailure(Call<Credentials> call, Throwable t) {
                listener.onLoginFailed();
            }
        });
    }
}
