package com.example.bookish;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bookish.Database.Book_Contract;
import com.example.bookish.Database.Book_DB_Helper;

import java.util.ArrayList;

public class Saved_Books_Activity extends AppCompatActivity {


    /** Adapter for the list of books */
    private SavedBookAdapter adapter;

    /** Arraylist for the list of books */
    private ArrayList<Book> saved_books;

    /** Database helper that will provide us access to the database */
    private Book_DB_Helper book_db_helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved__books_);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseSavedBooks();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseSavedBooks() {

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        book_db_helper = new Book_DB_Helper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = book_db_helper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Book_Contract.Saved_Book_Entry.COLUMN_BOOK_NAME,
                Book_Contract.Saved_Book_Entry.COLUMN_BOOK_SUBTITLE,
                Book_Contract.Saved_Book_Entry.COLUMN_BOOK_AUTHOR,
                Book_Contract.Saved_Book_Entry.COLUMN_BOOK_PUBLISHERS,
                Book_Contract.Saved_Book_Entry.COLUMN_BOOK_PAGES};

        // Perform a query on the saved books table
        Cursor cursor = db.query(
                Book_Contract.Saved_Book_Entry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order




            // Figure out the index of each column
            int nameColumnIndex = cursor.getColumnIndex(Book_Contract.Saved_Book_Entry.COLUMN_BOOK_NAME);
            int subtitleColumnIndex = cursor.getColumnIndex(Book_Contract.Saved_Book_Entry.COLUMN_BOOK_SUBTITLE);
            int authorColumnIndex = cursor.getColumnIndex(Book_Contract.Saved_Book_Entry.COLUMN_BOOK_AUTHOR);
            int publishersColumnIndex = cursor.getColumnIndex(Book_Contract.Saved_Book_Entry.COLUMN_BOOK_PUBLISHERS);
            int pagesColumnIndex = cursor.getColumnIndex(Book_Contract.Saved_Book_Entry.COLUMN_BOOK_PAGES);

            saved_books = new ArrayList<Book>();
            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                String name = cursor.getString(nameColumnIndex);
                String subtitle = cursor.getString(subtitleColumnIndex);
                String author = cursor.getString(authorColumnIndex);
                String publishers = cursor.getString(publishersColumnIndex);
                int pages = cursor.getInt(pagesColumnIndex);

                saved_books.add(new Book("not adding in database",name,subtitle,author,publishers,pages));
                Log.v("checkkkkkk",name);
        }

        // Always close the cursor when you're done reading from it. This releases all its
        // resources and makes it invalid.
        cursor.close();
        // Find a reference to the {@link ListView} in the layout
        ListView saved_books_list_View = findViewById(R.id.list_view_saved_books);

        // Create a new adapter that takes an empty list of earthquakes as input
        adapter = new SavedBookAdapter(Saved_Books_Activity.this, saved_books);

        // Set the adapter on the list view
        // so the list can be populated in the user interface
        saved_books_list_View.setAdapter(adapter);
    }

}
