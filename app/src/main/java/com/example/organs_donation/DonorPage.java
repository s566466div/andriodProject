package com.example.organs_donation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DonorPage extends AppCompatActivity {

    private EditText nameEditText, ageEditText, bloodGroupEditText, phoneEditText, healthEditText, addressEditText, hospitalNameEditText;
    private Toolbar organTB;
    private DatabaseReference databaseReference;
    private String selectedOrgan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donorpage);

        // Initialize Firebase Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("donors");

        // Initialize EditText fields
        nameEditText = findViewById(R.id.nameET);
        ageEditText = findViewById(R.id.ageET);
        bloodGroupEditText = findViewById(R.id.bloodGroupET);
        phoneEditText = findViewById(R.id.phoneET);
        healthEditText = findViewById(R.id.healthET);
        addressEditText = findViewById(R.id.addressET);
        hospitalNameEditText = findViewById(R.id.hospitalNameET);

        // Initialize Submit Button
        Button submitButton = findViewById(R.id.submitBTN);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                submitData();
            }
        });

        // Initialize Toolbar
        organTB = findViewById(R.id.organTB);
        setSupportActionBar(organTB);

        // Retrieve the donor object from the intent
        Donor donor = (Donor) getIntent().getSerializableExtra("donor");

        // Populate the EditText fields with donor details
        if (donor != null) {
            nameEditText.setText(donor.getName());
            ageEditText.setText(donor.getAge());
            bloodGroupEditText.setText(donor.getBloodGroup());
            phoneEditText.setText(donor.getPhone());
            healthEditText.setText(donor.getHealth());
            addressEditText.setText(donor.getAddress());
            hospitalNameEditText.setText(donor.getHospitalName());
        }
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.organ_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.blood_type_a_plus ||
                itemId == R.id.blood_type_b_plus ||
                itemId == R.id.blood_type_a_minus ||
                itemId == R.id.blood_type_b_minus ||
                itemId == R.id.blood_type_ab_plus ||
                itemId == R.id.blood_type_ab_minus ||
                itemId == R.id.blood_type_o_plus ||
                itemId == R.id.blood_type_o_minus) {
            String bloodType = item.getTitle().toString();
            handleBloodTypeSelection(bloodType);
            return true;
        } else if (itemId == R.id.organ_lungs ||
                itemId == R.id.organ_heart ||
                itemId == R.id.organ_kidney ||
                itemId == R.id.organ_liver ||
                itemId == R.id.organ_pancreas ||
                itemId == R.id.organ_small_bowel) {
            String organ = item.getTitle().toString();
            handleOrganSelection(organ);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }



    // Method to handle blood type selection
    private void handleBloodTypeSelection(String organ) {
        showToast("Blood Type " + organ + " selected");
        organTB.setTitle(organ);
        selectedOrgan = organ;
        // Additional logic if needed
    }

    // Method to handle organ selection
    private void handleOrganSelection(String organ) {
        showToast(organ + " selected");
        organTB.setTitle(organ);
        selectedOrgan = organ; // Store the selected organ
    }

    // Method to display a toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Method to save donor data to Firebase Database
    private void saveData() {
        // Retrieve data from EditText fields
        String name = nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String bloodGroup = bloodGroupEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String health = healthEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String hospitalName = hospitalNameEditText.getText().toString();

        // Ensure that all fields are filled and an organ is selected
        if (name.isEmpty() || age.isEmpty() || bloodGroup.isEmpty() || phone.isEmpty() || health.isEmpty() || address.isEmpty() || hospitalName.isEmpty() || selectedOrgan == null) {
            Toast.makeText(this, "Please fill in all fields and select an organ to donate", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Donor object with the retrieved data including the selected organ
        Donor donor = new Donor(name, age, bloodGroup, phone, health, address, hospitalName, selectedOrgan);

        // Save the data to Firebase database under the appropriate organ node
        DatabaseReference organReference = databaseReference.child(selectedOrgan.toLowerCase() + "Donors").push(); // Generate a unique key for the donor under the selected organ node
        organReference.setValue(donor)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Display a toast indicating successful data submission
                        Toast.makeText(DonorPage.this, "Data saved", Toast.LENGTH_SHORT).show();

                        // Clear EditText fields after successful submission
                        clearFields();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle error
                        Toast.makeText(DonorPage.this, "Error saving data. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Method to clear EditText fields after successful submission
    private void clearFields() {
        nameEditText.setText("");
        ageEditText.setText("");
        bloodGroupEditText.setText("");
        phoneEditText.setText("");
        healthEditText.setText("");
        addressEditText.setText("");
        hospitalNameEditText.setText("");
    }

    // Method to submit data and start DonorView activity
    private void submitData() {
        // Perform data submission to a server or other backend system

        // For demonstration purpose, simply display a toast
        Toast.makeText(this, "Data submitted", Toast.LENGTH_SHORT).show();

        // Start DonorView activity with the selected organ as an extra
        Intent intent = new Intent(this, DonorView.class);
        intent.putExtra("selectedOrgan", selectedOrgan);
        startActivity(intent);
    }

}
