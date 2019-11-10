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

        TextView tvDesc = convertView.findViewById(R.id.tvDesc);
        String details = parkingSpot.getDescription() + "\n" + parkingSpot.getSpaces() +
                "\n" + parkingSpot.getGeoLocalArea() + "\n" + parkingSpot.getLocation() +
                "\n" + Math.round(parkingSpot.getDistanceToDest()) + "m";
        tvDesc.setText(details);

        return convertView;
    }
}
