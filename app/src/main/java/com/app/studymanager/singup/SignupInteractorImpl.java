package com.app.studymanager.singup;

import com.app.studymanager.models.CommonResponse;
import com.app.studymanager.rest.APIError;
import com.app.studymanager.rest.ApiClient;
import com.app.studymanager.rest.AuthApi;
import com.app.studymanager.rest.ErrorUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 23-10-2016.
 */

public class SignupInteractorImpl implements SignupInteractor {
    @Override
    public void signup(String email, String password, final OnSignupFinishedListener listener) {

        AuthApi authApi = ApiClient.getClient().create(AuthApi.class);

        HashMap<String,String> body = new HashMap<String, String>();
        body.put("email", email);
        body.put("password", password);

        Call<CommonResponse> signup = authApi.signup(body);
        signup.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().isSuccess()){
                        listener.onSuccess();
                    } else {
                        listener.onEmailExist();
                    }
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    listener.onAPIError(error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

}
