package com.example.david.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;

class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private static ArrayList<Donation> donationData; ///////is static rn?
    private Context context;
    private static String locationName;


    public DonationAdapter (ArrayList<Donation> donationData, Context context, String locationName) {
        this.donationData = donationData;
        this.context = context;
        this.locationName = locationName;
    }
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // donation attributes
        private TextView time;
        private TextView location;
        private TextView shortDescription;
        private TextView fullDescription;
        private TextView value;
        private TextView category;
        private TextView comments;
        private TextView pictureURI;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });

            time = (TextView) view.findViewById(R.id.time);
            location = (TextView) view.findViewById(R.id.location);
            shortDescription = view.findViewById(R.id.shortDescrtion);
            fullDescription = (TextView) view.findViewById(R.id.fullDescription);
            value = (TextView) view.findViewById(R.id.value);
            category = (TextView) view.findViewById(R.id.category);
            // comments
            // picture URI??

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    int pos = getAdapterPosition();
                    Donation clickedItem = donationData.get(pos);

                    Intent intent = new Intent(context, DetailActivity.class);
                    // locationName is used to get the donations for a location
                    intent.putExtra("locationName", locationName);

                    context.startActivity(intent);
                }
            });
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param sDataSet Donation[] containing donation data to populate views to be used by RecyclerView.
     */
    public DonationAdapter(ArrayList<Donation> sDataSet) {
        donationData = sDataSet;
    }

    @Override
    public DonationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view, viewGroup, false);

        return new DonationAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DonationAdapter.ViewHolder viewHolder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        Donation donation = donationData.get(position);

        viewHolder

//        viewHolder.name.setText(location.getName());
//        viewHolder.type.setText(location.getType());
//        viewHolder.longitude.setText(location.getLongitude());
//        viewHolder.latitude.setText(location.getLatitude());
//        viewHolder.address.setText(location.getAddress());
//        viewHolder.phoneNumber.setText(location.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return donationData.size();
    }
}
