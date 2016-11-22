package com.app.studymanager.coursesettings;

import com.app.studymanager.models.CommonResponse;
import com.app.studymanager.models.CourseSettings;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.rest.APIError;
import com.app.studymanager.rest.ApiClient;
import com.app.studymanager.rest.CourseSettingsApi;
import com.app.studymanager.rest.ErrorUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 31-10-2016.
 */

public class CourseSettingsInteractorImpl implements CourseSettingsInteractor {
    @Override
    public void fetchCourseSettings(Credentials credentials, long courseId,
                                    final CourseSettingsInteractor.OnFinishedListener listener) {
        CourseSettingsApi api = ApiClient.getClient().create(CourseSettingsApi.class);

        HashMap<String,String> headers = new HashMap<>();
        headers.put("user-id", credentials.getUserId()+"");
        headers.put("auth-token", credentials.getAuthToken());

        Call<CourseSettings> courseSettings = api.getCourseSettings(headers, courseId);
        courseSettings.enqueue(new Callback<CourseSettings>(){

            @Override
            public void onResponse(Call<CourseSettings> call, Response<CourseSettings> response) {
                if(response.code() == 200){
                    listener.onFinished(response.body());
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    listener.onAPIError(error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CourseSettings> call, Throwable t) {
                listener.onError();
            }
        });
    }

    @Override
    public void saveCourseSettings(Credentials credentials,
                                   CourseSettings courseSettings, long courseId,
                                   final OnFinishedListener listener) {
        CourseSettingsApi api = ApiClient.getClient().create(CourseSettingsApi.class);

        HashMap<String,String> headers = new HashMap<>();
        headers.put("user-id", credentials.getUserId()+"");
        headers.put("auth-token", credentials.getAuthToken());

        Call<CommonResponse> saveSettings = api.saveCourseSettings(headers, courseSettings, courseId);
        saveSettings.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().isSuccess()){
                        listener.onSaved();
                    }else{
                        listener.onAPIError("Failed to save settings.");
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
