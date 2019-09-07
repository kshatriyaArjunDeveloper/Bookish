package com.example.bookish.Database;


/**
 * API Contract for the Bookish app.
 */
public class Book_Contract {

    /**...................CONSTRUCTOR..HERE.......................................................*/

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private Book_Contract() {}



    /**
     * Inner class that defines constant values for the books database table.
     * Each entry in the table represents a single book.
     */
    public static final class Saved_Book_Entry {

        /**...................CONSTANTS..HERE.......................................................*/


        /** Name of database table for books */
        public final static String TABLE_NAME = "saved_books";


        /**
         * Name of the book.
         *
         * Type: TEXT
         */
        public final static String COLUMN_BOOK_NAME ="name";

        /**
         * Subtitle of the book.
         *
         * Type: TEXT
         */
        public final static String COLUMN_BOOK_SUBTITLE = "subtitle";

        /**
         * AUTHOR of the book.
         *
         * Type: TEXT
         */
        public final static String COLUMN_BOOK_AUTHOR = "author";

        /**
         * PUBLISHER of the book.
         *
         * Type: TEXT
         */
        public final static String COLUMN_BOOK_PUBLISHERS = "publishers";

        /**
         * PUBLISHERS of the book.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_BOOK_PAGES = "pages";
    }


    /**...................METHODS..HERE.......................................................*/




}
