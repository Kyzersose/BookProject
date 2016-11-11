package com.theshaeffers.bookproject;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by shaefferm on 11/9/2016.
 */

public class VolumeLoader extends AsyncTaskLoader<List<Volume>> {

    //Tag for log messages
    private static final String LOG_TAG = VolumeLoader.class.getName();

    //Query URL string
    private String mUrl;

    public VolumeLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Volume> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        //Run the network request, parse the data, extract the list
        List<Volume> volumes = QueryUtils.fetchVolumeData(mUrl);
        return volumes;
    }
}
