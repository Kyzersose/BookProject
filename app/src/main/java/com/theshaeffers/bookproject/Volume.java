package com.theshaeffers.bookproject;

/**
 * Created by shaefferm on 11/4/2016.
 */

public class Volume {


    private String mTitle;
    private String mAuthors;
    private String mUrl;
    private String mDescription;

    public Volume(String title, String authors, String url, String description) {

        mTitle = title;
        mAuthors = authors;
        mUrl = url;
        mDescription = description;
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

    public String getmDescription() {
        return mDescription;
    }
}
