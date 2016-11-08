package com.app.studymanager.rest;

import com.app.studymanager.models.Book;
import com.app.studymanager.models.CommonResponse;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.UpdateBook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Vinay on 27-10-2016.
 */

public interface UserCourseApi {
    @Headers("content-type: application/json")
    @GET("api/subscribedCourses")
    Call<List<Course>> getSubscribedCourses(@HeaderMap Map<String,String> headers);

    @Headers("content-type: application/json")
    @GET("api/subscribedCourse/{id}")
    Call<Course> getSubscribedCourseDetails(@HeaderMap Map<String,String> headers,
                                  @Path("id") long courseId);

    @Headers("content-type: application/json")
    @POST("api/subscribedCourse/{id}/addCustomBook")
    Call<CommonResponse> addCustomBook(@HeaderMap Map<String,String> headers,
                                                @Body Book book,
                                                @Path("id")long courseId);


    @Headers("content-type: application/json")
    @POST("api/updateSubscribedCourse/{id}")
    Call<CommonResponse> updateSubscribedCourse(@HeaderMap Map<String,String> headers,
                                        @Body UpdateBook book,
                                        @Path("id")long courseId);

    @Headers("content-type: application/json")
    @POST("api/subscribeCourse/{id}")
    Call<CommonResponse> subscribeCourse(@HeaderMap Map<String,String> headers,
                                         @Path("id") long courseId);

    @Headers("content-type: application/json")
    @POST("api/unSubscribeCourse/{id}")
    Call<CommonResponse> unSubscribeCourse(@HeaderMap Map<String,String> headers,
                              @Path("id") long courseId);
}
