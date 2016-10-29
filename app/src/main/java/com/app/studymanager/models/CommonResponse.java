package com.app.studymanager.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vinay on 24-10-2016.
 */

public class CommonResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
