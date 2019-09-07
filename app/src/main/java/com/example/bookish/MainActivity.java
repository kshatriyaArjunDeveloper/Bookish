package com.example.bookish;

import android.animation.Animator;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>> {


    private static final String LOG_TAG = MainActivity.class.getName();
    /**
     * Constant value for the book loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOK_LOADER_ID = 1;
    /**
     * URL for book search data from the gbook api
     */
    public String GOOGLE_BOOK_SEARCH_REQUEST_URL;
    /**
     * Search bar to type what you want to search for
     */
    EditText search_view;
    /**
     * search button which start the search process
     */
    ImageButton search_button;
    /**
     * button which opens the saved books activity
     */
    FloatingActionButton saved_books_button;
    Intent intent_open_saved_booksActivity;
    // Get a reference to the LoaderManager, in order to interact with loaders.
    LoaderManager loaderManager;
    /**
     * Adapter for the list of books
     */
    private BookAdapter adapter;
    private Interpolator interpolator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Opening Saved Books Activity
        intent_open_saved_booksActivity = new Intent(MainActivity.this, Saved_Books_Activity.class);
        saved_books_button = findViewById(R.id.button_saved_book);
        saved_books_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent_open_saved_booksActivity);
            }
        });

        // Search button and search bar
        search_view = findViewById(R.id.editText_search);

        search_button = findViewById(R.id.imageButton_search);

        // Find a reference to the {@link ListView} in the layout
        ListView books_list_View = findViewById(R.id.list_view);

        // Create a new adapter that takes an empty list of earthquakes as input
        adapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        books_list_View.setAdapter(adapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            // Get a reference to the LoaderManager, in order to interact with loaders.
            loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);

            // Executing the search query
            search_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    GOOGLE_BOOK_SEARCH_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=" + search_view.getText().toString() + "&key=" + Book.KEY;

                    Log.v(LOG_TAG, "" + GOOGLE_BOOK_SEARCH_REQUEST_URL);

                    loaderManager.restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                }
            });
        }


    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new BookLoader(this, GOOGLE_BOOK_SEARCH_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> books) {
        // Clear the adapter of previous book data
        adapter.clear();

        // If there is a valid list of Books, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            adapter.addAll(books);
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {
        // Loader reset, so we can clear out our existing data.
        adapter.clear();
    }
}
