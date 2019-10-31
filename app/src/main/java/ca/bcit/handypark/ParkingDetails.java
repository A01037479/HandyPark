package ca.bcit.handypark;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class ParkingDetails extends AppCompatActivity {

    ArrayList<Parking> parkingSpots;
    Parking parkingSpot;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_details);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        parkingSpots = (ArrayList<Parking>) args.getSerializable("ARRAYLIST");

//        int index = (Integer) getIntent().getExtras().get("index");

        Parking parkingSpot = parkingSpots.get(0);
        TextView text = findViewById(R.id.text);
        TextView desc = findViewById(R.id.desc);

        String sourceString = "<b>Description: </b>" + parkingSpot.getDescription() +
                "<br/><b>Notes: </b>" + parkingSpot.getNotes() +
                "<br/><b>Parking Spaces: </b>" + parkingSpot.getSpaces() +
                "<br/><b>Coordinates: </b>" +  parkingSpot.getCoordinates()[0] +
                ", " + parkingSpot.getCoordinates()[1] +
                "<br/><b>Specific Location: </b>" + parkingSpot.getLocation() +
                "<br/><b>Area: </b>" + parkingSpot.getGeoLocalArea() + "<br/>";

        String descString = parkingSpot.getDescription();

        text.setText(Html.fromHtml(sourceString));
        desc.setText(Html.fromHtml(descString));
    }

}
