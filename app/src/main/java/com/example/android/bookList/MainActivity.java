package com.example.android.bookList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Timo on 06.07.2017.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Find the startSearch button
        Button search = (Button) findViewById(R.id.startSearch);

        // Set a click listener on that View
        search.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {

                // get search string
                EditText search = (EditText) findViewById(R.id.searchString);
                String searchString = search.getText().toString();

                // Create a new intent to open the {@link NumbersActivity}
                Intent searchIntent = new Intent(MainActivity.this, com.example.android.bookList.BookActivity.class);

                // pass search string to BookActivity
                searchIntent.putExtra("searchString",searchString);

                // Start the new activity
                startActivity(searchIntent);
            }
        });
    }
}