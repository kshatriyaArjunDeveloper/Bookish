package com.example.bookish;


/**
 * An {@link Book} object contains information related to a single book.
 */
public class Book {


    /**...................VARIABLES..HERE.......................................................*/


    /** gbook api KEY */
    public static final String KEY = "AIzaSyDqiuS8j2KgyJO9CleF-Q9C3eqJNb7uLCg";

    /** id of the Book by gbook */
    private String book_id;

    /** Name of the Book */
    private String book_name;

    /** Subtitle of the Book */
    private String book_subtitle;

    /** Author of the Book */
    private String book_author;

    /** Publishers of the Book */
    private String book_publishers;

    /** No. of pages of the Book */
    private int book_pages;


    /**...................CONSTRUCTOR..HERE.......................................................*/

    /**
     * Constructs a new {@link Book} object.
     *
     * @param book_id is the id of the book assigned by gbook
     * @param book_name is the name of the book
     * @param book_subtitle is the subtitle of the book
     * @param book_author is the name of the author
     * @param book_publishers is the name of publishers
     * @param book_pages is the total no. of pages of the book
     */
    public Book(String book_id, String book_name, String book_subtitle, String book_author, String book_publishers, int book_pages) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.book_subtitle = book_subtitle;
        this.book_author = book_author;
        this.book_publishers = book_publishers;
        this.book_pages = book_pages;
    }


    /**...................METHODS..HERE.......................................................*/


    public String getBook_id() {
        return book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getBook_subtitle() {
        return book_subtitle;
    }

    public String getBook_author() {
        return book_author;
    }

    public String getBook_publishers() {
        return book_publishers;
    }

    public int getBook_pages() {
        return book_pages;
    }
}
