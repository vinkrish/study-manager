package com.app.studymanager.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Vinay on 27-10-2016.
 */

public class Course implements Serializable{
    private long id;
    private String title;
    private String description;
    private String type;
    private Boolean subscribed;
    @SerializedName("bookList")
    private List<Book> bookList;
    private Integer preparationTimeInWeeks;
    private String startDate;
    private String endDate;
    private String currentStatus;
    private BigDecimal completionRate;
    private Integer todayGoal;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public Integer getPreparationTimeInWeeks() {
        return preparationTimeInWeeks;
    }

    public void setPreparationTimeInWeeks(Integer preparationTimeInWeeks) {
        this.preparationTimeInWeeks = preparationTimeInWeeks;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public BigDecimal getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(BigDecimal completionRate) {
        this.completionRate = completionRate;
    }

    public Integer getTodayGoal() {
        return todayGoal;
    }

    public void setTodayGoal(Integer todayGoal) {
        this.todayGoal = todayGoal;
    }
}
