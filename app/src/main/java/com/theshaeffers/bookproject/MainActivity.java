package com.theshaeffers.bookproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    //Variable used for log purposes.
    public static final String LOG_TAG = MainActivity.class.getName();

    public String searchRequestUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find the search button
        Button searchButton = (Button) findViewById(R.id.search_button);
        //Set the clicklistener for the search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            //Find the edittext field
            EditText userInput = (EditText) findViewById(R.id.userInput);

            @Override
            public void onClick(View v) {
                //Creates a string from the EditText input
                String bookQuery = userInput.getText().toString();
                //replace the blank spaces with %20 for proper url
                bookQuery.replaceAll(" ", "%20");
                //adds the Google Books API link to the user input and limits the results to 15.
                searchRequestUrl = getText(R.string.api_query).toString()
                        + bookQuery + getText(R.string.max_results).toString();
                //Intent used to pass the http query to the BooksListActivity
                Intent intent = new Intent(MainActivity.this, BookListActivity.class);
                intent.putExtra("infoPassed", searchRequestUrl);
                startActivity(intent);
            }
        });
    }


}







