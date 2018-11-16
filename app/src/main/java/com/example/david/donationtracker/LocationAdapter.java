package com.example.david.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private static List<Location> locationData;
    // --Commented out by Inspection (11/16/18 10:54 AM):private static String username;


    public LocationAdapter(ArrayList<Location> locationData) {
        LocationAdapter.locationData = locationData;

    }
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView type;
        private final TextView longitude;
        private final TextView latitude;
        private final TextView address;
        private final TextView phoneNumber;

        ViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            name = view.findViewById(R.id.name);
            type = view.findViewById(R.id.type);
            longitude = view.findViewById(R.id.longitude);
            latitude = view.findViewById(R.id.latitude);
            address = view.findViewById(R.id.address);
            phoneNumber = view.findViewById(R.id.phoneNumber);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    int pos = getAdapterPosition();
                    Location clickedItem = locationData.get(pos);
                    //not working properly
//                    if ((Credentials.getCurrentUser().getLocation() != clickedItem)
//                           && (Credentials.getCurrentUser().getUserType() == UserType.EMPLOYEE)) {
//                        //employee does not have access to this location
//                        int duration = Toast.LENGTH_SHORT;
//                        Toast toast = Toast.makeText(context, "Employee does
//                          ot have access to this location", duration);
//                        toast.show();
//                    } else {
                        Locations.setCurrentLocation(clickedItem);

                        Intent intent = new Intent(context, DetailActivity.class);
                        context.startActivity(intent);
//                    }
                }
            });
        }
    }

// --Commented out by Inspection START (11/16/18 10:46 AM):
//    /**
//     * Initialize the dataset of the Adapter.
//     *
//     * @param sDataSet Location[] containing location data to populate viewsto be
//     * used by RecyclerView.
//     */
//    public LocationAdapter(ArrayList<Location> sDataSet) {
//        locationData = sDataSet;
//    }
// --Commented out by Inspection STOP (11/16/18 10:46 AM)

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        Location location = locationData.get(position);


        viewHolder.name.setText(location.getName());
        viewHolder.name.setTextColor(Color.parseColor("#FFFFFF"));
        // Use this to change the color of the text in a recycler view
        viewHolder.type.setText(location.getType());
        viewHolder.longitude.setText(location.getLongitude());
        viewHolder.latitude.setText(location.getLatitude());
        viewHolder.address.setText(location.getAddress());
        viewHolder.phoneNumber.setText(location.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return locationData.size();
    }
}