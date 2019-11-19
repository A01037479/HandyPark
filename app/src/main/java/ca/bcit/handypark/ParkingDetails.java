package ca.bcit.handypark;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


import androidx.appcompat.app.AppCompatActivity;

public class ParkingDetails extends AppCompatActivity {

    ArrayList<Parking> parkingSpots = new ArrayList<>();
    ArrayList<Parking> allParkingSpots;
    ListView lvResults;
    Spinner spinnerRadius;
    Button selectRadius;
    TextView tvNoResults;
    int radius;
    double[] destCoords = new double[2];
    String googleMapsUrl = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_details);

        lvResults = findViewById(R.id.lvResults);
        tvNoResults = findViewById(R.id.tvNoResult);
        spinnerRadius = findViewById(R.id.spinnerRadius);
        selectRadius = findViewById(R.id.selectRadius);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        radius = intent.getIntExtra("RADIUS",1000);
        if (radius==0||radius==1000){
            spinnerRadius.setSelection(0);
        }
        else if (radius==3000){
            spinnerRadius.setSelection(1);
        }
        else if (radius==5000){
            spinnerRadius.setSelection(2);
        }

        destCoords = intent.getDoubleArrayExtra("DESTCOORDS");
        allParkingSpots = (ArrayList<Parking>) args.getSerializable("ARRAYLIST");
//        for (Parking parking:allParkingSpots){
//            parkingSpots.add(parking);
//        }

        for(Parking parking : allParkingSpots) {
            float distance = getDistanceToDest(parking.getCoordinates());
            if (distance <= (radius==0?1000:radius)) {
                parking.setDistanceToDest(distance);
                parkingSpots.add(parking);
            }
        }
        Collections.sort(parkingSpots,  Parking.COMPARE_BY_DISTANCE);
        spinnerRadius.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String radiusSelection = spinnerRadius.getSelectedItem().toString();
                System.out.println("Selected " + radiusSelection);
                if (radiusSelection.equals("1km"))
                    radius = 1000;
                else if (radiusSelection.equals("3km"))
                    radius = 3000;
                else
                    radius = 5000;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        selectRadius.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("RADIUS", radius);
                finish();
                startActivity(intent);
            }
        });
        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Assign dynamically generated URL to googleMapsURL
                googleMapsUrl = urlBuilder(i);
                Uri gmmIntentUri = Uri.parse(googleMapsUrl);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        if(parkingSpots.isEmpty()) {
            tvNoResults.setText("No results found for selected destination within the radius.");
            tvNoResults.setVisibility(View.VISIBLE);
//            Toast.makeText(ParkingDetails.this,
//                    "No matched results found for selected destination", Toast.LENGTH_LONG).show();
        }
        else {
            tvNoResults.setVisibility(View.GONE);
            ParkingAdapter adapter = new ParkingAdapter(ParkingDetails.this, parkingSpots);
            lvResults.setAdapter(adapter);
        }
    }

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
     * Dynamically generates URL based on three parameters:
     * 1. User's geolocation (automatically retrieved as origin is left blank)
     * 2. Parking meter selected in ParkingDetails activity, and
     * 3. Destination entered into search bar in MainActivity.
     * @param index as int (Index of the ListView item clicked)
     * @return url as String
     */
    public String urlBuilder(int index) {
        String baseUrl = getString(R.string.baseUrl);
        String destCoordsSnippet = destCoords[0] + "+" + destCoords[1];
        String destUrl = getString(R.string.destUrl);

        //Split parking location into words
        String[] parkingLocationArray = parkingSpots.get(index).getLocation().split(" ");
        //Trim off "North / South / East / West Side" in front of location
        String[] parkingLocationSubArray = Arrays.copyOfRange(parkingLocationArray, 2,
                parkingLocationArray.length);
        //Turn location back into String
        StringBuilder location = new StringBuilder();
        for (String str : parkingLocationSubArray) {
            location.append(str + " ");
        }

        return baseUrl + location + destUrl + destCoordsSnippet;
    }

}
