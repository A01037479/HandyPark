package ca.bcit.handypark;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class ParkingDetails extends AppCompatActivity {

    ArrayList<Parking> parkingSpots;
    Parking parkingSpot;
    ListView lvResults;
    double[] destCoords = new double[2];
    String destName = "";
    String googleMapsUrl = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_details);

        lvResults = findViewById(R.id.lvResults);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        destCoords = intent.getDoubleArrayExtra("DESTCORDS");
        destName = intent.getStringExtra("DESTNAME");
        parkingSpots = (ArrayList<Parking>) args.getSerializable("ARRAYLIST");
        Collections.sort(parkingSpots,  Parking.COMPARE_BY_DISTANCE);

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

        if(parkingSpots.isEmpty())
            Toast.makeText(ParkingDetails.this,
                    "No matched results found for selected destination", Toast.LENGTH_LONG).show();
        else {
            ParkingAdapter adapter = new ParkingAdapter(ParkingDetails.this, parkingSpots);
            lvResults.setAdapter(adapter);
        }
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

        String destNameSnippet = destName.replace(" ", "+");
        String destUrl = getString(R.string.destUrl);

        //Split parking location into words
        String[] parkingLocationArray = parkingSpots.get(index).getLocation().split(" ");
        //Trim off "North / South / East / West Side" in front of location
        String[] parkingLocationSubArray = Arrays.copyOfRange(parkingLocationArray, 3,
                parkingLocationArray.length);
        //Turn location back into String
        StringBuilder location = new StringBuilder();
        for (String str : parkingLocationSubArray) {
            location.append(str + " ");
        }

        return baseUrl + location + destUrl + destNameSnippet;
    }

}
