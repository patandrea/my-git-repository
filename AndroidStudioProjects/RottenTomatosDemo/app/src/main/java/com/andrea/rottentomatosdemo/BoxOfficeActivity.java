/*******************************************************
 * Name:        BoxOfficeActivity
 * Description: Responsible for fetching and deserializing
 *              the data and configuring the adapter.
 *********************************************************/
package com.andrea.rottentomatosdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BoxOfficeActivity extends Activity {
    public static final String TAG = "BoxOfficeActivity";
    private ListView lvMovies;
    private BoxOfficeAdapter adapterMovies;
    private RottenTomatoesClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "*** In onCreate() ***");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_rotten_tomatoes);

        lvMovies = (ListView) findViewById(R.id.lvMovies);
        ArrayList<BoxOfficeMovie> aMovies = new ArrayList<BoxOfficeMovie>();
        adapterMovies = new BoxOfficeAdapter(this, aMovies);
        lvMovies.setAdapter(adapterMovies);

        // Fetch the data remotely
        fetchBoxOfficeMovies();
    }
// Executes an API call to the box office endpoint, parses the results
        // Converts them into an array of movie objects and adds them to the adapter
    private void fetchBoxOfficeMovies() {
        client = new RottenTomatoesClient();
        client.getBoxOfficeMovies(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray items = null;
                try {
                    // Get the movies json array
                    items = response.getJSONArray("movies");
                    // Parse json array into array of model objects
                    ArrayList<BoxOfficeMovie> movies = BoxOfficeMovie.fromJson(items);
                    // Load model objects into the adapter
                    for (BoxOfficeMovie movie : movies) {
                        adapterMovies.add(movie); // add movie through the adapter
                    }
                    adapterMovies.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}