package com.example.android.bookList;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.bookList.QueryUtils;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<com.example.android.bookList.Book>> {

    /** Tag for log messages */
    private static final String LOG_TAG = BookLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link BookLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<com.example.android.bookList.Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of books.
        List<com.example.android.bookList.Book> books = QueryUtils.fetchBookData(mUrl);
        return books;
    }
}