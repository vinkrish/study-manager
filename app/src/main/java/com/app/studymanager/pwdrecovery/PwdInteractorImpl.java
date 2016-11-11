package com.app.studymanager.pwdrecovery;

import com.app.studymanager.models.CommonResponse;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.rest.ApiClient;
import com.app.studymanager.rest.AuthApi;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 11-11-2016.
 */

public class PwdInteractorImpl implements PwdInteractor {
    @Override
    public void resetPwd(Credentials credentials, String email, String password,
                         final OnFinishedListener listener) {
        AuthApi authApi = ApiClient.getClient().create(AuthApi.class);

        HashMap<String,String> headers = new HashMap<>();
        headers.put("user-id", credentials.getUserId()+"");
        headers.put("auth-token", credentials.getAuthToken());

        HashMap<String,String> body = new HashMap<String, String>();
        body.put("email", email);
        body.put("password", password);

        Call<CommonResponse> resetPwd = authApi.resetPassword(headers,body);
        resetPwd.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.body().isSuccess()){
                    listener.onPwdReset();
                } else {
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                listener.onError();
            }
        });
    }
}
