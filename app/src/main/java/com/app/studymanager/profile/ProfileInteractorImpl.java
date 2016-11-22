package com.app.studymanager.profile;

import com.app.studymanager.models.CommonResponse;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.models.Profile;
import com.app.studymanager.models.SubscribedCourses;
import com.app.studymanager.rest.APIError;
import com.app.studymanager.rest.ApiClient;
import com.app.studymanager.rest.AuthApi;
import com.app.studymanager.rest.ErrorUtils;
import com.app.studymanager.rest.UserCourseApi;
import com.app.studymanager.subscribedcourses.SubscribedCoursesInteractor;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 11-11-2016.
 */

public class ProfileInteractorImpl implements ProfileInteractor {
    @Override
    public void getProfile(Credentials credentials, final OnFinishedListener listener) {
        AuthApi authApi = ApiClient.getClient().create(AuthApi.class);

        HashMap<String,String> headers = new HashMap<>();
        headers.put("user-id", credentials.getUserId()+"");
        headers.put("auth-token", credentials.getAuthToken());

        Call<Profile> sendNewPwd = authApi.getUserProfile(headers);
        sendNewPwd.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if(response.isSuccessful()){
                    listener.onFinished(response.body());
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    listener.onAPIError(error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                listener.onError();
            }
        });
    }

    @Override
    public void updateProfile(Credentials credentials, String name, final OnFinishedListener listener) {
        AuthApi authApi = ApiClient.getClient().create(AuthApi.class);

        HashMap<String,String> headers = new HashMap<>();
        headers.put("user-id", credentials.getUserId()+"");
        headers.put("auth-token", credentials.getAuthToken());

        HashMap<String,String> body = new HashMap<>();
        body.put("name", name);

        Call<CommonResponse> sendNewPwd = authApi.updateUserProfile(headers, body);
        sendNewPwd.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().isSuccess()){
                        listener.onSuccess();
                    } else {
                        listener.onAPIError("Failed to update profile.");
                    }
                }else {
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

    @Override
    public void fetchSubscribedCourses(Credentials credentials, final OnFinishedListener listener) {
        UserCourseApi api = ApiClient.getClient().create(UserCourseApi.class);

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("user-id", credentials.getUserId()+"");
        hashMap.put("auth-token", credentials.getAuthToken());

        Call<SubscribedCourses> subscribedCourses = api.getSubscribedCourses(hashMap);
        subscribedCourses.enqueue(new Callback<SubscribedCourses>() {
            @Override
            public void onResponse(Call<SubscribedCourses> call, Response<SubscribedCourses> response) {
                if(response.isSuccessful()) {
                    listener.onFinished(response.body());
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    listener.onAPIError(error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SubscribedCourses> call, Throwable t) {
                listener.onError();
            }
        });
    }

}
