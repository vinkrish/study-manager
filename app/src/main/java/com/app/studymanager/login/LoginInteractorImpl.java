package com.app.studymanager.login;

import android.text.TextUtils;

import com.app.studymanager.models.LoginResponse;
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
        if(TextUtils.isEmpty(email)){
            listener.onEmailError();
            return;
        }
        if(!validateEmail(email)){
            listener.onValidEmailError();
            return;
        }
        if(TextUtils.isEmpty(password)){
            listener.onPasswordError();
            return;
        }
        AuthApi authApi = ApiClient.getClient().create(AuthApi.class);

        HashMap<String,String> body = new HashMap<String, String>();
        body.put("email", email);
        body.put("password", password);

        Call<LoginResponse> login = authApi.login(body);
        login.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.body().isSuccess()){
                    listener.onSuccess(response.body());
                } else {
                    listener.onLoginFailed();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                listener.onLoginFailed();
            }
        });
    }

     private boolean validateEmail(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else return false;
    }
}
