package com.app.studymanager.singup;

import android.text.TextUtils;

import com.app.studymanager.models.CommonResponse;
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

public class SignupInteractorImpl implements SignupInteractor {
    @Override
    public void signup(String email, String password, final OnSignupFinishedListener listener) {
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
        if(validatePassword(password)){
            listener.onValidPasswordError();
            return;
        }
        AuthApi authApi = ApiClient.getClient().create(AuthApi.class);

        HashMap<String,String> body = new HashMap<String, String>();
        body.put("email", email);
        body.put("password", password);

        Call<CommonResponse> signup = authApi.signup(body);
        signup.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.body().isSuccess()){
                    listener.onSuccess();
                } else {
                    listener.onEmailExist();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    private boolean validateEmail(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validatePassword(String password) {
        return password.length() < 8;
    }

}
