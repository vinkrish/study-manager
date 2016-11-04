package com.app.studymanager.models;

/**
 * Created by Vinay on 04-11-2016.
 */

public class UpdateBook {
    private long bookId;
    private int noOfPagesRead;

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public int getNoOfPagesRead() {
        return noOfPagesRead;
    }

    public void setNoOfPagesRead(int noOfPagesRead) {
        this.noOfPagesRead = noOfPagesRead;
    }
}
