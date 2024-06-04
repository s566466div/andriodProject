package com.example.organs_donation;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class smallBowelList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DonorAdapter donorAdapter;
    private List<Donor> donorList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smallbowellist);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.smallbowelView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        donorList = new ArrayList<>();
        donorAdapter = new DonorAdapter(this, donorList,false,true);
        recyclerView.setAdapter(donorAdapter);

        // Get reference to Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("donors").child("small bowelDonors");

        // Fetch donor details from Firebase
        fetchDonorDetails();
    }

    private void fetchDonorDetails() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Retrieve donor details and add to the list
                    Donor donor = snapshot.getValue(Donor.class);
                    donorList.add(donor);
                }
                // Update RecyclerView with the new data
                donorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Toast.makeText(smallBowelList.this, "Failed to fetch donor details: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
