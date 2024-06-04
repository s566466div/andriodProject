package com.example.organs_donation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class a_postivefrag extends Fragment {


    private RecyclerView recyclerView;
    private DonorAdapter donorAdapter;
    private List<Donor> donorList;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_postivefrag, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.apostiveView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        donorList = new ArrayList<>();
        donorAdapter = new DonorAdapter(getActivity(), donorList, false, true); // Pass the required parameters
        recyclerView.setAdapter(donorAdapter);
        // Get reference to Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("donors").child("a+Donors");

        // Fetch donor details from Firebase
        fetchDonorDetails();

        return view;
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
                Toast.makeText(getActivity(), "Failed to fetch donor details: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
