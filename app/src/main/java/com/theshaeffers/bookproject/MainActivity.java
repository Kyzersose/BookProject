package com.theshaeffers.bookproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
                String bookQuery = userInput.getText().toString();
                searchRequestUrl = getText(R.string.api_query).toString()
                        + bookQuery + getText(R.string.max_results).toString();
                Intent intent = new Intent(MainActivity.this, BookListActivity.class);
                intent.putExtra("infoPassed", searchRequestUrl);
                startActivity(intent);
            }
        });
        Log.e("MainActivity", "searchRequestUrk: " + searchRequestUrl);
    }


}







