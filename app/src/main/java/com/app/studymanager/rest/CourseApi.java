package com.app.studymanager.rest;

import com.app.studymanager.models.Course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Vinay on 27-10-2016.
 */

public interface CourseApi {
    @Headers("content-type: application/json")
    @GET("api/courses")
    Call<List<Course>> getCourses(@HeaderMap Map<String,String> headers);

    @Headers("content-type: application/json")
    @GET("api/course/{id}")
    Call<Course> getCourseDetails(@HeaderMap Map<String,String> headers,
                              @Path("id") long courseId);
}
