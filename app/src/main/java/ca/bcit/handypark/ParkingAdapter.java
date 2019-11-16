package ca.bcit.handypark;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ParkingAdapter extends ArrayAdapter<Parking> {
    Context _context;
    public ParkingAdapter(Context context, ArrayList<Parking> parkingSpots) {
        super(context, 0, parkingSpots);
        _context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Activity activity = (Activity) _context;
        Parking parkingSpot = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row_layout, parent, false);
        }

        TextView tvDesc1 = convertView.findViewById(R.id.tvDesc1);
        TextView tvDesc2 = convertView.findViewById(R.id.tvDesc2);
        TextView tvDesc3 = convertView.findViewById(R.id.tvDesc3);
        TextView tvDesc4 = convertView.findViewById(R.id.tvDesc4);
//        String details = parkingSpot.getDescription() + "\nSpaces: " + parkingSpot.getSpaces() +
//                "\n" + parkingSpot.getGeoLocalArea() + "\n" + parkingSpot.getLocation() +
//                "\n" + Math.round(parkingSpot.getDistanceToDest()) + "m";
        if(parkingSpot.getDescription().contains("meter")){
            tvDesc3.setText("Meter parking");
        }
        else{
            tvDesc3.setText("Free parking");
        }
        tvDesc1.setText(parkingSpot.getLocation());
        tvDesc2.setText("Spaces: " + parkingSpot.getSpaces());
        tvDesc4.setText(""+Math.round(parkingSpot.getDistanceToDest())+" m");

        return convertView;
    }
}
