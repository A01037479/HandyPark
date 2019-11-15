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
                Intent intent = new Intent(ParkingDetails.this, MapsActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST", parkingSpots);
                intent.putExtra("INDEX",i);
                intent.putExtra("BUNDLE",args);
                intent.putExtra("DESTINATION",destCoords);
                startActivity(intent);
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
