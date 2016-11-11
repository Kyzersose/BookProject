package com.theshaeffers.bookproject;

/**
 * Created by shaefferm on 11/4/2016.
 */

public class Volume {


    private String mTitle;
    private String mAuthors;
    private String mUrl;

    public Volume(String title, String authors, String url) {

        mTitle = title;
        mAuthors = authors;
        mUrl = url;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmAuthors() {
        return mAuthors;
    }

    public String getmUrl() {
        return mUrl;
    }
}
