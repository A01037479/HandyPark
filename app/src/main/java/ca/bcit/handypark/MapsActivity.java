package ca.bcit.handypark;

import androidx.constraintlayout.solver.widgets.Helper;
import androidx.fragment.app.FragmentActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double[] destCoords = new double[2];
    String destName;
    LatLng latLng;
    ArrayList<Parking> parkingResults = new ArrayList<>();
    Parking parking = null;
    String overviewPolylineString;
    List<LatLng> path;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        destName = intent.getStringExtra("destName");
//        destCoords = intent.getDoubleArrayExtra("destCoords");
//        dest = intent.getStringExtra("dest");

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        parkingResults = (ArrayList<Parking>) bundle.getSerializable("ARRAYLIST");
        destCoords = intent.getDoubleArrayExtra("DESTINATION");
        int index = (Integer)intent.getExtras().get("INDEX");
        parking = parkingResults.get(index);
        new MapsActivity.GetLatLng().execute();






    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng parkingLatLng = new LatLng(parking.getCoordinates()[0],parking.getCoordinates()[1]);
        LatLng destinationLatLng = new LatLng(destCoords[0], destCoords[1]);
        mMap.addMarker(new MarkerOptions().position(parkingLatLng).title(parking.getLocation())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.addMarker(new MarkerOptions().position(destinationLatLng).title("Destination")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.color(Color.RED);
        polyOptions.width(20);

        //      draw polyline
        for (LatLng wayPoint : path){
            polyOptions.add(wayPoint);
        }

        mMap.addPolyline(polyOptions);




        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinationLatLng, 16.0f));
    }

    private class GetLatLng extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MapsActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = null;

            jsonStr = sh.makeServiceCall("https://maps.googleapis.com/maps/api/directions/json?origin=49.291635%2C-123.135596&destination=49.283667%2C-123.114970&waypoints=49.291635%2C-123.135596&key=" + getString(R.string.google_maps_key));

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray records = jsonObj.getJSONArray("routes");
                    JSONObject route1 = records.getJSONObject(0);
                    JSONObject overviewPolyline = route1.getJSONObject("overview_polyline");
                    overviewPolylineString = overviewPolyline.getString("points");
                    path = PolyUtil.decode(overviewPolylineString);

                } catch (final JSONException e) {
//                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
//                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
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

            //Toon[] toonArray = toonList.toArray(new Toon[toonList.size()]);

//            ParkingAdapter adapter = new ParkingAdapter(MainActivity.this, parkingArrayList);

            // Attach the adapter to a ListView
//            lv.setAdapter(adapter);
            System.out.println("post:" + destCoords[0]+":"+destCoords[1]);
        }

    }
}
