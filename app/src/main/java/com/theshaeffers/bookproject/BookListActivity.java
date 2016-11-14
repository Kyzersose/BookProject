package com.theshaeffers.bookproject;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Volume>> {

    //Adapter for the list of Volumes
    private VolumeAdapter mAdapter;

    //TextView that is displayed when the list is empty
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        //Find a reference to the {@link ListView} in the layout
        ListView volumeListView = (ListView) findViewById(R.id.book_list_view);

        //Set the {@link mEmptyStateTextView} as the empty view for the list view
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        volumeListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of volumes as input
        mAdapter = new VolumeAdapter(this, new ArrayList<Volume>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        volumeListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected volume.
        volumeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current volume that was clicked on
                Volume currentVolume = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri volumeUri = Uri.parse(currentVolume.getmUrl());

                // Create a new intent to view the volume URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, volumeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader.
            loaderManager.initLoader(1, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Volume>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        Intent intent = getIntent();
        String searchRequestUrl = intent.getStringExtra("infoPassed");
        return new VolumeLoader(this, searchRequestUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Volume>> loader, List<Volume>volumes) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No books found."
        mEmptyStateTextView.setText(R.string.no_books);

        // Clear the adapter of previous volume data
        mAdapter.clear();

        // If there is a valid list of {@link Volume}, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (volumes != null && !volumes.isEmpty()) {
            mAdapter.addAll(volumes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Volume>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
