
        Locations locations = new Locations();

        final Location location = Locations.get(locationName);
        final User user = Credentials.get(username);

        adapter = new DonationAdapter(Donations.getDonations(locations.get(locationName)), null, username);
        locationRecyclerView = findViewById(R.id.locationRecyclerView);
        locationRecyclerView.setHasFixedSize(true);
        locationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        locationRecyclerView.setAdapter(adapter);
        TextView textView = findViewById(R.id.detailText);
        if (location != null) {
            String detailText = "Name: " + location.getName();
            detailText = detailText + "\nType: " + location.getType()
                    + "\nLongitude: " + location.getLongitude() + "\nLatitude: "
                    + location.getLatitude() + "\nAddress: " + location.getAddress()
                    + "\nPhone Number: " + location.getPhoneNumber() + "\n";
        Button donationButton = findViewById(R.id.donationButton);
        donationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((user.getUserType() == UserType.EMPLOYEE) ||
                        (user.getUserType() == UserType.ADMIN) ||
                        (user.getUserType() == UserType.MANAGER)) {
                    Intent intent = new Intent(DetailActivity.this, DonationActivity.class);
                    intent.putExtra("location", location.getName());
                    intent.putExtra("username", username);
                    final LocalDateTime time = LocalDateTime.now();
                    intent.putExtra("time", time);
                    startActivity(intent);
                    finish();
                } else {
                    Log.i("","User type is not employee");
                    //Toaster if no access
                    String text = "You don't have permission to access this.";
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
