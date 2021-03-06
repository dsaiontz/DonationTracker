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

import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private final List<Donation> donationData;

    /**
     * Constructor for the search adapter class
     * @param donationData is the value for the field
     */
    public SearchAdapter(List<Donation> donationData) {
        this.donationData = donationData;
    }
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView shortDescription;
        private final TextView value;

        ViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            shortDescription = view.findViewById(R.id.donationShortDescription);
            value = view.findViewById(R.id.donationValue);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    int pos = getAdapterPosition();
                    Donation clickedItem = donationData.get(pos);
                    //Log.e("","clickedItem: " + clickedItem + ", currentuser.getlocation: "
                    // + Credentials.getCurrentUser().getLocation());
                    //not working properly
//                    if ((Credentials.getCurrentUser().getLocation() != clickedItem)
//                            && (Credentials.getCurrentUser().getUserType() == UserType.EMPLOYEE)){
//                        //employee does not have access to this location
//                        int duration = Toast.LENGTH_SHORT;
//                        Toast toast = Toast.makeText(context, "Employee does not have access to
// this location", duration);
//                        toast.show();
//                    } else {
                    //Locations.setCurrentLocation(clickedItem);

                    Intent intent = new Intent(context, DonationDetailActivity.class);
                    Donations.setCurrentDonation(clickedItem);

                    Log.e("", "This is before starting the new activity");

                    context.startActivity(intent);
//                    }
                }
            });
        }
    }

// --Commented out by Inspection START (11/16/18 10:30 AM):
//    /**
//     * Initialize the dataset of the Adapter.
//     *
//     * @param sDataSet Location[] containing location data to populate views to be used
//     * by RecyclerView.
//     */
//    public SearchAdapter(ArrayList<Donation> sDataSet) {
//        donationData = sDataSet;
//    }
// --Commented out by Inspection STOP (11/16/18 10:30 AM)

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View v = layoutInflater
                .inflate(R.layout.recycler_view_donations, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Donation donation = donationData.get(position);
        Log.e("",Double.toString(donation.getValue()));
        Log.e("",donation.getShortDescription());

        viewHolder.shortDescription.setText(donation.getShortDescription());
//        viewHolder.name.setTextColor(Color.parseColor("#FFFFFF"));  // Use this
// to change the color of the text in a recycler view
        String v = Double.toString(donation.getValue());
        viewHolder.value.setText(v);
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