package ca.bcit.handypark;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
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

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String dest = "";
    double lat;
    double lng;
    LatLng latLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        dest = intent.getStringExtra("dest");
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
        List<Address> addressList = null;
        mMap = googleMap;

        if (dest != null && dest != "") {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(dest, 5);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Address adr = addressList.get(0);
            latLng = new LatLng(adr.getLatitude(), adr.getLongitude());

        }

        // East Side 500 Richards St
        LatLng parking1 = new LatLng(49.282943, -123.11344);
        // East Side 1100 Burrard St
        LatLng parking2 = new LatLng(49.279453, -123.128447);
        // East Side 1100 Homer St
        LatLng parking3 = new LatLng(49.2753257, -123.123063);
        // South Side 100 W Cordova St
        LatLng parking4 = new LatLng(49.2831648, -123.108411);
        // West Side 700 Hornby St
        LatLng parking5 = new LatLng(49.282822, -123.121647);

        mMap.addMarker(new MarkerOptions().position(latLng).title(dest));
        mMap.addMarker(new MarkerOptions().position(parking1).title("parking" + " 1")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.addMarker(new MarkerOptions().position(parking2).title("parking" + " 2")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.addMarker(new MarkerOptions().position(parking3).title("parking" + " 3")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.addMarker(new MarkerOptions().position(parking4).title("parking" + " 4")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.addMarker(new MarkerOptions().position(parking5).title("parking" + " 5")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
    }
}
