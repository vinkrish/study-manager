package com.app.studymanager.rest;

import com.app.studymanager.models.CommonResponse;
import com.app.studymanager.models.CourseSettings;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Vinay on 31-10-2016.
 */

public interface CourseSettingsApi {
    @Headers("content-type: application/json")
    @GET("api/subscribedCourse/{id}/settings")
    Call<CourseSettings> getCourseSettings(@HeaderMap Map<String,String> headers,
                                           @Path("id") long courseId);

    @Headers("content-type: application/json")
    @POST("api/subscribedCourse/{id}/settings")
    Call<CommonResponse> saveCourseSettings(@HeaderMap Map<String,String> headers,
                                            @Body CourseSettings body,
                                            @Path("id") long courseId);
}
