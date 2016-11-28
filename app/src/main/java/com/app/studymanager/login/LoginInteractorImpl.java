package com.app.studymanager.login;

import com.app.studymanager.models.CommonResponse;
import com.app.studymanager.models.Credentials;
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

public class LoginInteractorImpl implements LoginInteractor {
    @Override
    public void login(String email, String password, final OnLoginFinishedListener listener) {

        AuthApi authApi = ApiClient.getClient().create(AuthApi.class);

        HashMap<String,String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        Call<Credentials> login = authApi.login(body);
        login.enqueue(new Callback<Credentials>() {
            @Override
            public void onResponse(Call<Credentials> call, Response<Credentials> response) {
                if(response.isSuccessful()) {
                    if(response.body().isSuccess()){
                        listener.onSuccess(response.body());
                    } else {
                        listener.onAPIError(response.body().getMessage());
                    }
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    listener.onAPIError(error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Credentials> call, Throwable t) {
                listener.onError();
            }
        });
    }

    @Override
    public void recoverPwd(String email, final OnLoginFinishedListener listener) {
        AuthApi authApi = ApiClient.getClient().create(AuthApi.class);

        HashMap<String,String> body = new HashMap<String, String>();
        body.put("email", email);

        Call<CommonResponse> sendNewPwd = authApi.newPassword(body);
        sendNewPwd.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().isSuccess()){
                        listener.onPwdRecovered();
                    } else {
                        listener.onNoUser();
                    }
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    listener.onAPIError(error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                listener.onError();
            }
        });
    }
}
