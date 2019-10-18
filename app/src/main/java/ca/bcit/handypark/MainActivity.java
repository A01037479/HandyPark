package ca.bcit.handypark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    double lat;
    double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(btnLstnr);
    }

    private View.OnClickListener btnLstnr = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText etDestination = findViewById(R.id.etDestination);
            String dest = etDestination.getText().toString();
            // Explicit intent
            // Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            intent.putExtra("dest", dest);
//            intent.putExtra("lat", lat);
//            intent.putExtra("lng", lng);
            startActivity(intent);
        }
    };

//    public void onSearch(View v) {
//        List<Address> addressList = null;
//
//        EditText editTextLocation = findViewById(R.id.etDestination);
//        String location = editTextLocation.getText().toString();
//        Toast toast2 = Toast.makeText(getApplicationContext(), location ,Toast.LENGTH_LONG);
//        toast2.show();
//        if (location != null && location != "") {
//            Geocoder geocoder = new Geocoder(this);
//            try {
//                addressList = geocoder.getFromLocationName(location, 1);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            Address adr = addressList.get(0);
//            lat = adr.getLatitude();
//            lng = adr.getLongitude();
//
////            Toast toast = Toast.makeText(getApplicationContext(),"location not null",Toast.LENGTH_LONG);
////            toast.show();
////            mMap.clear();
////            mMap.addMarker(new MarkerOptions().position(latLng).title(location));
////            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//        }
//    }
}
