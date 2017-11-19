package com.example.android.bookList;

public class Book {

        private String mThumbnail;
        private String mTitle;
        private String[] mAuthors;
        private String mPublisher;
        private String mPublishedDate;
        private String mUrl;

        /**
         * Create a new Book object.
         *
         * @param thumbnail
         * @param title
         * @param authors
         * @param publisher
         * @param publishedDate
         * @param url
         */
        public Book (String thumbnail,String title,String[] authors,String publisher,String publishedDate,String url) {
            mThumbnail = thumbnail;
            mTitle = title;
            mAuthors = authors;
            mPublisher = publisher;
            mPublishedDate = publishedDate;
            mUrl = url;
        }

        public String getThumbnail() {
            return mThumbnail;
        }

        public String getTitle() {
        return mTitle;
    }

        public String[] getAuthors() {
            return mAuthors;
        }

        public String getPublisher() {
            return mPublisher;
        }

        public String getPublishedDate() {
            return mPublishedDate;
        }

        public String getUrl() { return mUrl; }

}
