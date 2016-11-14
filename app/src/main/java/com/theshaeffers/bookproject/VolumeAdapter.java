package com.theshaeffers.bookproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * The {@link VolumeAdapter} creates list of Volumes. The code to reconstruct the Strings to
 * url's, find the different views, and place the data in the correct spaces.
 * Created by shaefferm on 11/9/2016.
 */

public class VolumeAdapter extends ArrayAdapter<Volume> {

    public VolumeAdapter(Context context, List<Volume> volumes) {
        super(context, 0, volumes);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.volume_list_item, parent, false);
        }

        //locate the volume at the position in the list.
        Volume currentVolume = getItem(position);

        //Find the TextView for the title
        TextView titleView = (TextView) listItemView.findViewById(R.id.titleTextView);
        //Set the text of the {@link titleTextView}
        titleView.setText(currentVolume.getmTitle());

        //Find the TextView for the authors
        TextView authorView = (TextView) listItemView.findViewById(R.id.authorsTextView);
        //String pulled from current Volume object.
        String authorsComplete = currentVolume.getmAuthors();
        //Removing the trailing ", " from the authors string
        authorsComplete = authorsComplete.substring(0, authorsComplete.length() - 2);
        //Set the text of the {@link authorsTextView}
        authorView.setText(authorsComplete);

        //Find the TextView for description
        TextView descriptionView = (TextView) listItemView.findViewById(R.id.descriptionTextView);
        //set the text of the descriptionTextView
        descriptionView.setText(currentVolume.getmDescription());

        //Return the list of Volumes
        return listItemView;
    }
}
