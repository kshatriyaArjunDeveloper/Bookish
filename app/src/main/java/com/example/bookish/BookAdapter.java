package com.example.bookish;

import android.animation.Animator;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookish.Database.Book_Contract;
import com.example.bookish.Database.Book_DB_Helper;

import java.util.List;


/**
 * An {@link BookAdapter} knows how to create a list item layout for each book
 * in the data source (a list of {@link Book} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class BookAdapter extends ArrayAdapter<Book> {


    /**
     * Constructs a new {@link BookAdapter}.
     *
     * @param context of the app
     * @param books is the list of books, which is the data source of the adapter
     */
    public BookAdapter(Context context,List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Find the book at the given position in the list of books
        final Book currentbook = getItem(position);

        // Find the TextView with view ID textView_book_name
        TextView book_name_View = listItemView.findViewById(R.id.textView_book_name);

        // Display the book name of the current book in that TextView
        book_name_View.setText(currentbook.getBook_name());

        // Find the TextView with view ID textView_book_author
        TextView book_author_View = listItemView.findViewById(R.id.textView_book_author);

        // Display the book author of the current book in that TextView
        book_author_View.setText(currentbook.getBook_author());

        // Find the TextView with view ID textView_book_subtitle
        TextView book_subtitle_View = listItemView.findViewById(R.id.textView_book_subtitle);

        // Display the subtitles of the current book in that TextView
        book_subtitle_View.setText(currentbook.getBook_subtitle());

        // Find the TextView with view ID textView_book_publishers
        TextView book_publishers_View = listItemView.findViewById(R.id.textView_book_publishers);

        // Display the publishers name of the current book in that TextView
        book_publishers_View.setText(currentbook.getBook_publishers());

        // Find the TextView with view ID textView_book_pages
        TextView book_pages_View = listItemView.findViewById(R.id.textView_book_pages);

        // Display the pages count of the current book in that TextView
        book_pages_View.setText(Integer.toString(currentbook.getBook_pages()));

        // Display the book name of the current book in that TextView
        book_name_View.setText(currentbook.getBook_name());

        // Save Button for saving the current book
        final Button button = listItemView.findViewById(R.id.button_save_this_book);
        // Cache row position inside the button using `setTag`
        button.setTag(position);
        // Attach the click event handler
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /** insert book into database */

                // Create database helper
                Book_DB_Helper book_db_helper = new Book_DB_Helper(getContext());

                // Gets the database in write mode
                SQLiteDatabase db = book_db_helper.getWritableDatabase();

                // Create a ContentValues object where column names are the keys,
                // and book attributes from the array list are the values.
                ContentValues values = new ContentValues();
                values.put(Book_Contract.Saved_Book_Entry.COLUMN_BOOK_NAME, currentbook.getBook_name());
                values.put(Book_Contract.Saved_Book_Entry.COLUMN_BOOK_SUBTITLE, currentbook.getBook_subtitle());
                values.put(Book_Contract.Saved_Book_Entry.COLUMN_BOOK_AUTHOR, currentbook.getBook_author());
                values.put(Book_Contract.Saved_Book_Entry.COLUMN_BOOK_PUBLISHERS, currentbook.getBook_publishers());
                values.put(Book_Contract.Saved_Book_Entry.COLUMN_BOOK_PAGES, currentbook.getBook_pages());

                // Insert a new row for pet in the database, returning the ID of that new row.
                long newRowId = db.insert(Book_Contract.Saved_Book_Entry.TABLE_NAME, null, values);

                // Show a toast message depending on whether or not the insertion was successful
                if (newRowId == -1) {
                    // If the row ID is -1, then there was an error with insertion.
                    Toast.makeText(getContext(), "Error with saving book", Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the insertion was successful and we can display a toast with the row ID.
                    Toast.makeText(getContext(), "Book saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
                }


            }
        });


        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

}
