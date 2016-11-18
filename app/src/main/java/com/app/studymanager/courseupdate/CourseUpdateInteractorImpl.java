package com.app.studymanager.courseupdate;

import com.app.studymanager.models.Book;
import com.app.studymanager.models.CommonResponse;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.models.UpdateBook;
import com.app.studymanager.rest.ApiClient;
import com.app.studymanager.rest.UserCourseApi;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 03-11-2016.
 */

public class CourseUpdateInteractorImpl implements CourseUpdateInteractor {
    @Override
    public void fetchCourseDetails(Credentials credentials, long courseId, final OnFinishedListener listener) {
        UserCourseApi api = ApiClient.getClient().create(UserCourseApi.class);

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("user-id", credentials.getUserId()+"");
        hashMap.put("auth-token", credentials.getAuthToken());

        Call<Course> courseDetails = api.getSubscribedCourseDetails(hashMap, courseId);
        courseDetails.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                listener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                listener.onError();
            }
        });
    }

    @Override
    public void updateSubscribedCourse(Credentials credentials, long courseId,
                                       Book book, final OnFinishedListener listener) {
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
                if(response.body().isSuccess()){
                    listener.onUpdated();
                }else{
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                listener.onError();
            }
        });
    }

    @Override
    public void unSubscribeToCourse(Credentials credentials, long courseId,
                                    final OnFinishedListener listener) {
        UserCourseApi api = ApiClient.getClient().create(UserCourseApi.class);

        HashMap<String,String> headers = new HashMap<>();
        headers.put("user-id", credentials.getUserId()+"");
        headers.put("auth-token", credentials.getAuthToken());

        Call<CommonResponse> unsubscribeCourse = api.unSubscribeCourse(headers, courseId);
        unsubscribeCourse.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.body().isSuccess()){
                    listener.onUnSubscribed();
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

    @Override
    public void deleteBook(Credentials credentials, long courseId, long bookId,
                           final OnFinishedListener listener) {
        UserCourseApi api = ApiClient.getClient().create(UserCourseApi.class);

        HashMap<String,String> headers = new HashMap<>();
        headers.put("user-id", credentials.getUserId()+"");
        headers.put("auth-token", credentials.getAuthToken());

        Call<CommonResponse> unsubscribeCourse = api.deleteBook(headers, courseId, bookId);
        unsubscribeCourse.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.body().isSuccess()){
                    listener.onDeleted();
                } else {
                    listener.onCantDelete();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                listener.onError();
            }
        });
    }
}
