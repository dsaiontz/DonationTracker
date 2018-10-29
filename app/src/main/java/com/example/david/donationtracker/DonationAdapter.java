package com.example.david.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
        //private TextView comments;
        //private TextView pictureURI;

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
            shortDescription = view.findViewById(R.id.shortDescription);
            fullDescription = (TextView) view.findViewById(R.id.fullDescription);
            value = (TextView) view.findViewById(R.id.value);
            category = (TextView) view.findViewById(R.id.category);
            // comments
            // picture URI??

//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Context context = v.getContext();
//                    int pos = getAdapterPosition();
//                    Donation clickedItem = donationData.get(pos);
//
//                    Intent intent = new Intent(context, DetailActivity.class);
//                    // locationName is used to get the donations for a location
//                    intent.putExtra("locationName", locationName);
//
//                    context.startActivity(intent);
//                }
//            });
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
                .inflate(R.layout.recycler_view_detail_view, viewGroup, false);

        return new DonationAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DonationAdapter.ViewHolder viewHolder, int position) {
        Log.d(TAG, "Element " + position + " set.");
        Donation donation = donationData.get(position);
        if (donation == null) {
            viewHolder.time.setText("no donations");
            viewHolder.location.setText("");
            viewHolder.shortDescription.setText("");
            viewHolder.fullDescription.setText("");
            viewHolder.value.setText("");
            viewHolder.category.setText("");
        } else {

            viewHolder.time.setText(donation.getTime().toString());
            viewHolder.location.setText(donation.getLocation().toString());
            viewHolder.shortDescription.setText(donation.getShortDescription());
            viewHolder.fullDescription.setText(donation.getFullDescription());
            viewHolder.value.setText(Double.toString(donation.getValue()));
            viewHolder.category.setText(donation.getCategory().toString());
        }
    }

    @Override
    public int getItemCount() {
        if (donationData == null) {
            return 0;
        } else {
            return donationData.size();
        }
    }
}