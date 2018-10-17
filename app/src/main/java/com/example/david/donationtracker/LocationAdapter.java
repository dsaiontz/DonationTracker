package com.example.david.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import java.lang.reflect.Array;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private static ArrayList<Location> locationData; ///////is static rn?
    private Context context;


    public LocationAdapter (ArrayList<Location> locationData, Context context) {
        this.locationData = locationData;
        this.context = context;
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

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            name = (TextView) view.findViewById(R.id.name);
            type = (TextView) view.findViewById(R.id.type);
            longitude = (TextView) view.findViewById(R.id.longitude);
            latitude = (TextView) view.findViewById(R.id.latitude);
            address = (TextView) view.findViewById(R.id.address);
            phoneNumber = (TextView) view.findViewById(R.id.phoneNumber);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    int pos = getAdapterPosition();
                    Location clickedItem = locationData.get(pos);
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("location", (Parcelable) clickedItem);
                    context.startActivity(intent);
                }
            });
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param sDataSet Location[] containing location data to populate views to be used by RecyclerView.
     */
    public LocationAdapter(ArrayList<Location> sDataSet) {
        locationData = sDataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        Location location = locationData.get(position);

        viewHolder.name.setText(location.getName());
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