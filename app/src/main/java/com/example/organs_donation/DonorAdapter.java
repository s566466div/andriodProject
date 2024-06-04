package com.example.organs_donation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.DonorViewHolder> {
    private Context context;
    private List<Donor> donorList;
    private boolean showButtons;
    private boolean bookbutton;

    public DonorAdapter(Context context, List<Donor> donorList, boolean showButtons, boolean bookbutton) {
        this.context = context;
        this.donorList = donorList;
        this.showButtons = showButtons;
        this.bookbutton = bookbutton;
    }

    @NonNull
    @Override
    public DonorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new DonorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonorViewHolder holder, int position) {
        Donor donor = donorList.get(position);
        holder.nameText.setText("Name: " + donor.getName());
        holder.address.setText("Address: " + donor.getAddress());
        holder.organ.setText("Organ: " + donor.getOrganToDonate());
        holder.age.setText("Age: " + donor.getAge());
        holder.hospital.setText("Hospital: " + donor.getHospitalName());
        holder.phone.setText("Phone Number: " + donor.getPhone());
        if (showButtons) {
            holder.editButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setVisibility(View.VISIBLE);
        } else {
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
        }
        if (bookbutton) {
            holder.bookButton.setVisibility(View.VISIBLE);
        } else {
            holder.bookButton.setVisibility(View.GONE);
        }

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass the donor object with the intent
                Intent intent = new Intent(context, DonorPage.class);
                intent.putExtra("donor", donor);
                context.startActivity(intent);
            }
        });



        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Donor donor = donorList.get(position);
                    if (donor != null) {
                        String donorId = donor.getName();
                        if (donorId != null) {
                            DatabaseReference donorRef = FirebaseDatabase.getInstance().getReference().child("donors").child(donorId);
                            donorRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    donorList.remove(position);
                                    notifyItemRemoved(position);
                                    Toast.makeText(context, "Donor data deleted successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Failed to delete donor data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            }
        });

        holder.bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Donor donor = donorList.get(position);
                    if (donor != null) {
                        String donorId = donor.getName();
                        if (donorId != null) {
                            DatabaseReference donorRef = FirebaseDatabase.getInstance().getReference().child("donors").child(donorId);
                            donorRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    donorList.remove(position);
                                    notifyItemRemoved(position);
                                    Toast.makeText(context, "Organ Booked successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Failed to Book Organ: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return donorList.size();
    }

    public static class DonorViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, address, organ, age, hospital, phone;
        Button editButton, deleteButton, bookButton;

        public DonorViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            address = itemView.findViewById(R.id.address);
            organ = itemView.findViewById(R.id.lastDonated);
            age = itemView.findViewById(R.id.tvage);
            hospital = itemView.findViewById(R.id.hospital);
            phone = itemView.findViewById(R.id.phone);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            bookButton = itemView.findViewById(R.id.bookButton);
        }
    }
}
