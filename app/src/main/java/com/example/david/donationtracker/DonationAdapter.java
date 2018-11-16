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

import java.util.ArrayList;
import java.util.List;

class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private static List<Donation> donationData;
    // --Commented out by Inspection (11/16/18 10:45 AM):private static String locationName;


    public DonationAdapter(List<Donation> donationData) {
        DonationAdapter.donationData = donationData;
    }
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // donation attributes
        private final TextView time;
        private final TextView location;
        private final TextView shortDescription;
        private final TextView fullDescription;
        private final TextView value;
        private final TextView category;
        //private TextView comments;
        //private TextView pictureURI;

        ViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });

            time = view.findViewById(R.id.time);
            location = view.findViewById(R.id.location);
            shortDescription = view.findViewById(R.id.shortDescription);
            fullDescription = view.findViewById(R.id.fullDescription);
            value = view.findViewById(R.id.value);
            category = view.findViewById(R.id.category);
            // comments
            // picture URI??

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    int pos = getAdapterPosition();
                    Donation clickedItem = donationData.get(pos);

                    Intent intent = new Intent(context, DonationDetailActivity.class);
                    Donations.setCurrentDonation(clickedItem);

                    context.startActivity(intent);
                }
            });
        }
    }

// --Commented out by Inspection START (11/16/18 10:30 AM):
//    /**
//     * Initialize the dataset of the Adapter.
//     *
//     * @param sDataSet Donation[] containing donation data to populate
//     * views to be used by RecyclerView.
//     */
//    public DonationAdapter(ArrayList<Donation> sDataSet) {
//        donationData = sDataSet;
//    }
// --Commented out by Inspection STOP (11/16/18 10:30 AM)

    @NonNull
    @Override
    public DonationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                                         int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_detail_view, viewGroup, false);

        return new DonationAdapter.ViewHolder(v);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onBindViewHolder(@NonNull DonationAdapter.ViewHolder viewHolder, int position) {
        Log.d(TAG, "Element " + position + " set.");
        Donation donation = donationData.get(position);
        if (donationData.isEmpty()) {
            viewHolder.time.setText("no donations");
            viewHolder.location.setText("");
            viewHolder.shortDescription.setText("");
            viewHolder.fullDescription.setText("");
            viewHolder.value.setText("");
            viewHolder.category.setText("");
        } else {

            viewHolder.time.setText(donation.getTime());
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