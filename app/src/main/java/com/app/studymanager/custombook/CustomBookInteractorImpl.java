package com.app.studymanager.custombook;

import com.app.studymanager.models.Book;
import com.app.studymanager.models.CommonResponse;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.rest.APIError;
import com.app.studymanager.rest.ApiClient;
import com.app.studymanager.rest.ErrorUtils;
import com.app.studymanager.rest.UserCourseApi;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 08-11-2016.
 */

public class CustomBookInteractorImpl implements CustomBookInteractor {
    @Override
    public void addCustomBook(Credentials credentials, long courseId, Book book,
                              final OnFinishedListener listener) {
        UserCourseApi api = ApiClient.getClient().create(UserCourseApi.class);

        HashMap<String,String> headers = new HashMap<>();
        headers.put("user-id", credentials.getUserId()+"");
        headers.put("auth-token", credentials.getAuthToken());

        Call<CommonResponse> updateCourse = api.addCustomBook(headers, book, courseId);
        updateCourse.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().isSuccess()){
                        listener.onAdded();
                    }else{
                        listener.onAPIError("Failed to add book.");
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
