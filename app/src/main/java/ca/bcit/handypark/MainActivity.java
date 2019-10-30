package ca.bcit.handypark;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    double lat;
    double lng;
    // Retrieves name of class (MainActivity) for Log purposes
    private String TAG = MainActivity.class.getSimpleName();
    // Loading message
    private ProgressDialog pDialog;
    // To display book titles
    private ListView lv;
    // URL to get contacts JSON
    private static String SERVICE_URL = "https://opendata.vancouver.ca/api/records/1.0/search/?dataset=disability-parking&rows=9999&facet=description&facet=notes&facet=geo_local_area";
    // Stores list of books retrieved from API
    private ArrayList<LatLng> latLngArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(btnLstnr);
    }

    /**
     * When "Submit" is clicked, open new intent and pass the search param.
     */
    private View.OnClickListener btnLstnr = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText etDestination = findViewById(R.id.etDestination);
            String dest = etDestination.getText().toString();
            // Explicit intent
            // Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            intent.putExtra("dest", dest);
            startActivity(intent);
        }
    };

//    /**
//     * Async task class to get json by making HTTP call
//     */
//    private class GetLatLngs extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            // Showing progress dialog
//            pDialog = new ProgressDialog(MainActivity.this);
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(false);
//            pDialog.show();
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... arg0) {
//            HttpHandler sh = new HttpHandler();
//            String jsonStr = null;
//
//            // Making a request to url and getting response
//            jsonStr = sh.makeServiceCall(SERVICE_URL);
//
//            Log.e(TAG, "Response from url: " + jsonStr);
//
//            if (jsonStr != null) {
//                try {
//
////                    fields: {
////                        description: "Designated meter parking space",
////                                notes: "No stopping accessible zone",
////                                spaces: 1,
////                                geom: {
////                            type: "Point",
////                                    coordinates: [
////                            -123.069865,
////                                    49.27218
////]
////                        },
////                        location: "North Side 1600 Kitchener St",
////                                geo_local_area: "Grandview-Woodland"
////                    },
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//                    JSONArray records = jsonObj.getJSONArray("records");
//                    for (int i = 0; i < records.length(); i++) {
//                        JSONObject f = records.getJSONObject(i).getJSONObject("fields");
//                    }
////                    JSONArray items = jsonObj.getJSONArray("items");
////                    for (int i = 0; i < items.length(); i++) {
////                        JSONObject b = items.getJSONObject(i).getJSONObject("volumeInfo");
////                        String title = b.getString("title");
////                        String thumbnail = b.getJSONObject("imageLinks").getString("smallThumbnail");
//////                        String authors = b.getString("authors");
////                        String authors = "";
////                        JSONArray authorArray = b.getJSONArray("authors");
//////                        authors = authorArray.join(", ");
////                        String[] authorStr = new String[authorArray.length()];
////                        for (int j = 0; j < authorStr.length; j++) {
////                            authorStr[j] = authorArray.getString(j);
////                        }
////                        authors = TextUtils.join(", ", authorStr);
////                        String publisher = b.getString("publisher");
////                        String publishedDate = b.getString("publishedDate");
////                        String desc = "";
////                        if (b.has("description")) {
////                            desc = b.getString("description");
////                        }
////                        String isbn = b.getJSONArray("industryIdentifiers").getJSONObject(1).getString("identifier");
////
////                        Book book = new Book();
////                        book.setTitle(title);
////                        book.setThumbnail(thumbnail);
////                        book.setAuthors(authors);
////                        book.setPublisher(publisher);
////                        book.setPublishedDate(publishedDate);
////                        book.setDescription(desc);
////                        book.setIsbn(isbn);
////
////                        bookList.add(book);
//                    //}
//                } catch (final JSONException e) {
//                    Log.e(TAG, "Json parsing error: " + e.getMessage());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG)
//                                    .show();
//                        }
//                    });
//                }
//            } else {
//                Log.e(TAG, "Couldn't get json from server.");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG)
//                                .show();
//                    }
//                });
//
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//
//            // Dismiss the progress dialog
//            if (pDialog.isShowing())
//                pDialog.dismiss();
//
//            //Toon[] toonArray = toonList.toArray(new Toon[toonList.size()]);
//
//            BookAdapter adapter = new BookAdapter(MainActivity.this, bookList);
//
//            // Attach the adapter to a ListView
//            lv.setAdapter(adapter);
//        }
//    }

}
