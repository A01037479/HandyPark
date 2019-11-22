package ca.bcit.handypark;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;

import android.widget.ListView;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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
    private static String SERVICE_URL = "https://opendata.vancouver.ca/api/records/1.0/search/?dataset=disability-parking&rows=1000&facet=description&facet=notes&facet=geo_local_area";
    private ArrayList<Parking> parkingArrayList;
    private ArrayList<Parking> topResultsArrayList;
    private String destName = "";
    private String destAddress = "";
    private double[] destCoords = new double[2];
    private static final int RADIUS = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = getString(R.string.google_maps_key);

        // Initialize the SDK
        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(), apiKey);
        }

        // Create a new Places client instance
        PlacesClient placesClient = Places.createClient(this);

        final AutocompleteSupportFragment f = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        f.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        f.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                topResultsArrayList.clear();
//                destCoords[0] = place.getLatLng().latitude;
//                destCoords[1] = place.getLatLng().longitude;
                Array.set(destCoords, 0,place.getLatLng().latitude);
                Array.set(destCoords, 1,place.getLatLng().longitude);
                destName = place.getName();
                destAddress = place.getAddress();
//                Toast.makeText(MainActivity.this, destName + " " + destCoords[0]+destCoords[1], Toast.LENGTH_LONG).show();
                System.out.println("inside"+destCoords[0]+":"+destCoords[1]);

                for(Parking parking : parkingArrayList) {
                    float distance = getDistanceToDest(parking.getCoordinates());
                    if (distance <= RADIUS) {
                        parking.setDistanceToDest(distance);
                        topResultsArrayList.add(parking);
                    }
                }

                //TO PARKING DETAILS (JSON STUFF)
                // J S O N
                Intent intent = new Intent(MainActivity.this, ParkingDetails.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST", topResultsArrayList);
                intent.putExtra("BUNDLE",args);
                intent.putExtra("DESTCOORDS",destCoords);
                intent.putExtra("RADIUS", 1000);
                startActivity(intent);

                if (place.getLatLng() != null) {
                    destCoords[0] = place.getLatLng().latitude;
                    destCoords[1] = place.getLatLng().longitude;
                    destName = place.getName();
                }

            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        parkingArrayList = new ArrayList<Parking>();
        topResultsArrayList = new ArrayList<Parking>();
        new GetParking().execute();

        System.out.println(destCoords[0]+":"+destCoords[1]);

    }

    /**
     * Calculates distance between destination and a parking meter.
     * @param parkingCoords double[]
     * @return float
     */
    private float getDistanceToDest(double[] parkingCoords){
        Location dest = new Location("");
        Location parking = new Location("");
        dest.setLatitude(destCoords[0]);
        dest.setLongitude(destCoords[1]);
        parking.setLatitude(parkingCoords[0]);
        parking.setLongitude(parkingCoords[1]);
        return dest.distanceTo(parking);
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetParking extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = null;

            jsonStr = sh.makeServiceCall(SERVICE_URL);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray records = jsonObj.getJSONArray("records");
                    for (int i = 0; i < records.length(); i++) {
                        JSONObject f = records.getJSONObject(i).getJSONObject("fields");
                        String description = f.getString("description");
                        String notes = f.getString("notes");
                        int spaces = f.getInt("spaces");
                        JSONObject g = f.getJSONObject("geom");
                        JSONArray jsonCoords = g.getJSONArray("coordinates");
                        double[] coordinates = new double[2];
                        coordinates[0] = jsonCoords.getDouble(1);
                        coordinates[1] = jsonCoords.getDouble(0);
                        String location = f.getString("location");
                        String geoLocalArea = f.getString("geo_local_area");

                        Parking parkingSpot = new Parking();
                        parkingSpot.setDescription(description);
                        parkingSpot.setNotes(notes);
                        parkingSpot.setSpaces(spaces);
                        parkingSpot.setCoordinates(coordinates);
                        parkingSpot.setLocation(location);
                        parkingSpot.setGeoLocalArea(geoLocalArea);

                        // Entry with incorrect address value that defaults to the Cambie bridge
                        String badEntryLocation = getString(R.string.badEntryLocation);
                        if (!parkingSpot.getLocation().contains(badEntryLocation)) {
                            parkingArrayList.add(parkingSpot);
                        }

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG)
//                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG)
//                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (pDialog.isShowing())
                pDialog.dismiss();

            System.out.println("post:" + destCoords[0]+":"+destCoords[1]);
        }


    }

}
