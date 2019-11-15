package ca.bcit.handypark;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import androidx.appcompat.app.AppCompatActivity;

public class ParkingDetails extends AppCompatActivity {

    ArrayList<Parking> parkingSpots;
    Parking parkingSpot;
    ListView lvResults;
    double[] destCoords = new double[2];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_details);

        lvResults = findViewById(R.id.lvResults);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        destCoords = intent.getDoubleArrayExtra("DESTINATION");
        parkingSpots = (ArrayList<Parking>) args.getSerializable("ARRAYLIST");
        Collections.sort(parkingSpots,  Parking.COMPARE_BY_DISTANCE);

        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(ParkingDetails.this, MapsActivity.class);
//                Bundle args = new Bundle();
//                args.putSerializable("ARRAYLIST", parkingSpots);
//                intent.putExtra("INDEX",i);
//                intent.putExtra("BUNDLE",args);
//                intent.putExtra("DESTINATION",destCoords);
//                startActivity(intent);


                Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=49.27218,-123.069865&waypoints=49.236842,-123.159807|49.2835,-123.1153&destination=49.286148,-123.111500");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }

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

}
