package com.example.organs_donation;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        // Initialize buttons
        Button btnDonorPage = findViewById(R.id.btnDonorPage);
        Button btnReceiverPage = findViewById(R.id.btnRegisterNow);
        Button btnBloodDonorsPage = findViewById(R.id.btnblood);

        // Set onClickListener for Donor Page Button
        btnDonorPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Donor Page
                Intent intent = new Intent(help.this, DonorPage.class);
                startActivity(intent);
            }
        });

        // Set onClickListener for Receiver Page Button
        btnReceiverPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Receiver Page
                Intent intent = new Intent(help.this, ReceiverHomePage.class);
                startActivity(intent);
            }
        });

        // Set onClickListener for Blood Donors Page Button
        btnBloodDonorsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Blood Donors Page
                Intent intent = new Intent(help.this, BloodDonationPage.class);
                startActivity(intent);
            }
        });
    }
}
