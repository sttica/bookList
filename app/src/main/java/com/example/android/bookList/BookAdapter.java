package com.example.android.bookList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<com.example.android.bookList.Book> {

        public BookAdapter(Activity context, ArrayList<com.example.android.bookList.Book> books) {
            super(context, 0, books);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Check if the existing view is being reused, otherwise inflate the view
            View listItemView = convertView;
            if(listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item, parent, false);
            }

            // Get the {@link AndroidFlavor} object located at this position in the list
            com.example.android.bookList.Book currentBook = getItem(position);

            ImageView thumbnailView = (ImageView) listItemView.findViewById(R.id.thumbnail);
            String thumbnail = currentBook.getThumbnail();
            try {
                URL thumbnailUrl = new URL(thumbnail);
                Picasso.with(getContext()).load(thumbnail).into(thumbnailView);
            }
            catch (MalformedURLException ignore){
            }

            TextView titleView = (TextView) listItemView.findViewById(R.id.title);
            String title = currentBook.getTitle();
            titleView.setText(title);

            TextView authorView = (TextView) listItemView.findViewById(R.id.author);
            String[] authors = currentBook.getAuthors();
            String authorsConcat = "";
            for (int i = 0; i < authors.length; i++) {
                authorsConcat = authorsConcat + authors[i];
            }
            authorView.setText(authorsConcat);

            TextView publisherView = (TextView) listItemView.findViewById(R.id.publisher);
            String publisher = currentBook.getPublisher();
            publisherView.setText(publisher);

            TextView publishedDateView = (TextView) listItemView.findViewById(R.id.publishedDate);
            String publishedDate = currentBook.getPublishedDate();
            publishedDateView.setText(publishedDate);


            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("mailto:"));

            // Return the list item view that is now showing the appropriate data
            return listItemView;
        }

}
