package com.app.studymanager.subscribedcourses;

import com.app.studymanager.models.Book;
import com.app.studymanager.models.CommonResponse;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.models.SubscribedCourses;
import com.app.studymanager.models.UpdateBook;
import com.app.studymanager.rest.APIError;
import com.app.studymanager.rest.ApiClient;
import com.app.studymanager.rest.ErrorUtils;
import com.app.studymanager.rest.UserCourseApi;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 27-10-2016.
 */

public class SubscribedCoursesInteractorImpl implements SubscribedCoursesInteractor {
    @Override
    public void fetchSubscribedCourses(int userId, String authToken, final OnFinishedListener listener) {
        UserCourseApi api = ApiClient.getClient().create(UserCourseApi.class);

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("user-id", userId+"");
        hashMap.put("auth-token", authToken);

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

    @Override
    public void saveBookRead(Credentials credentials, long courseId, Book book, final OnFinishedListener listener) {
        UserCourseApi api = ApiClient.getClient().create(UserCourseApi.class);

        HashMap<String,String> headers = new HashMap<>();
        headers.put("user-id", credentials.getUserId()+"");
        headers.put("auth-token", credentials.getAuthToken());

        UpdateBook updateBook = new UpdateBook();
        updateBook.setBookId(book.getId());
        updateBook.setNoOfPagesRead(book.getNoOfPagesRead());
        updateBook.setRevisionCompleted(book.isRevisionCompleted());

        Call<CommonResponse> updateCourse = api.updateSubscribedCourse(headers, updateBook, courseId);
        updateCourse.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().isSuccess()){
                        listener.onSaved();
                    }else{
                        listener.onAPIError("Failed to update course.");
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
