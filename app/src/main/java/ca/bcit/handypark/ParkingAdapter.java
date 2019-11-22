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

        TextView tvParkingLocation = convertView.findViewById(R.id.tvParkingLocation);
        TextView tvParkingSpaces = convertView.findViewById(R.id.tvParkingSpaces);
        TextView tvMeterOrFree = convertView.findViewById(R.id.tvMeterOrFree);
        TextView tvDistanceFromDest = convertView.findViewById(R.id.tvDistanceFromDest);

        String meter = convertView.getContext().getString(R.string.meter);

        if(parkingSpot.getDescription().contains(meter)){
            tvMeterOrFree.setText(convertView.getContext().getString(R.string.meterParking));
        } else{
            tvMeterOrFree.setText(convertView.getContext().getString(R.string.freeParking));
        }

        tvParkingLocation.setText(parkingSpot.getLocation());
        String spacesDesc = convertView.getContext().getString(R.string.spacesDesc)
                + parkingSpot.getSpaces();
        tvParkingSpaces.setText(spacesDesc);
        String distanceFromDest = Math.round(parkingSpot.getDistanceToDest()) +
                convertView.getContext().getString(R.string.metreDistance);
        tvDistanceFromDest.setText(distanceFromDest);

        return convertView;
    }
}
