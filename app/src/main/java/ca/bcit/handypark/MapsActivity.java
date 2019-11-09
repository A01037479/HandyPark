package ca.bcit.handypark;

import androidx.constraintlayout.solver.widgets.Helper;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

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

        List<LatLng> wayPoints = new ArrayList<>();
        wayPoints.add(destinationLatLng);
        wayPoints.add(parkingLatLng);
        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.color(Color.RED);
        polyOptions.width(5);
        polyOptions.addAll(wayPoints);

        mMap.addPolyline(polyOptions);




        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinationLatLng, 16.0f));
    }

}
