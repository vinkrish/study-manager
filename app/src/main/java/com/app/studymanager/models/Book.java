package com.app.studymanager.models;

/**
 * Created by Vinay on 27-10-2016.
 */

public class Book {
    private long id;
    private String title;
    private String description;
    private String type;
    private String author;
    private int noOfPages;
    private int noOfPagesRead;
    private int noOfPagesUnRead;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNoOfPages() {
        return noOfPages;
    }

    public void setNoOfPages(int noOfPages) {
        this.noOfPages = noOfPages;
    }

    public int getNoOfPagesRead() {
        return noOfPagesRead;
    }

    public void setNoOfPagesRead(int noOfPagesRead) {
        this.noOfPagesRead = noOfPagesRead;
    }

    public int getNoOfPagesUnRead() {
        return noOfPagesUnRead;
    }

    public void setNoOfPagesUnRead(int noOfPagesUnRead) {
        this.noOfPagesUnRead = noOfPagesUnRead;
    }
}
