package com.example.organs_donation;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DonorView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DonorAdapter donorAdapter;
    private List<Donor> donorList;
    private DatabaseReference databaseReference;
    private String selectedOrgan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donor_view);

        // Retrieve the selected organ from the intent that started this activity
        selectedOrgan = getIntent().getStringExtra("selectedOrgan");

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.donorRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Donor list
        donorList = new ArrayList<>();

        // Initialize DonorAdapter with showButtons parameter set to true
        donorAdapter = new DonorAdapter(this, donorList, true, false);

        // Set adapter to RecyclerView
        recyclerView.setAdapter(donorAdapter);

        // Load donors
        loadDonors();
    }

    private void loadDonors() {
        // Initialize Firebase Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        // Initialize database reference for the selected organ type
        if (selectedOrgan != null) {
            DatabaseReference databaseReference = firebaseDatabase.getReference("donors").child(selectedOrgan.toLowerCase() + "Donors");

            // Listen for child events in the database reference
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                    // Retrieve the new donor added to the database
                    Donor donor = dataSnapshot.getValue(Donor.class);
                    // Add the donor to the list
                    donorList.add(donor);
                    // Notify the adapter of the data change
                    donorAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                // Implement other methods like onChildChanged, onChildRemoved, onChildMoved if needed

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                    Toast.makeText(DonorView.this, "Failed to load donors: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Handle error if selectedOrgan is null
            Toast.makeText(DonorView.this, "Selected organ is null", Toast.LENGTH_SHORT).show();
        }
    }

}
