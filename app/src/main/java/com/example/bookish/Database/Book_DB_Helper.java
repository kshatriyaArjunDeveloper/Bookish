package com.example.bookish.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Book_DB_Helper extends SQLiteOpenHelper {


    /**...................VARIABLES..AND..CONSTANTS..HERE.......................................................*/


    public static final String LOG_TAG = Book_DB_Helper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "bookish.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;




    /**...................CONSTRUCTOR..HERE.......................................................*/


    /**
     * Constructs a new instance of {@link Book_DB_Helper}.
     *
     * @param context of the app
     */
    public Book_DB_Helper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    /**...................METHODS..HERE.......................................................*/


    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create a String that contains the SQL statement to create the saved_books table
        String SQL_CREATE_SAVED_BOOKS_TABLE =  "CREATE TABLE " + Book_Contract.Saved_Book_Entry.TABLE_NAME + " ("
                + Book_Contract.Saved_Book_Entry.COLUMN_BOOK_NAME + " TEXT,"
                + Book_Contract.Saved_Book_Entry.COLUMN_BOOK_SUBTITLE + " TEXT,"
                + Book_Contract.Saved_Book_Entry.COLUMN_BOOK_AUTHOR + " TEXT,"
                + Book_Contract.Saved_Book_Entry.COLUMN_BOOK_PUBLISHERS + " TEXT,"
                + Book_Contract.Saved_Book_Entry.COLUMN_BOOK_PAGES + " INTEGER);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_SAVED_BOOKS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
