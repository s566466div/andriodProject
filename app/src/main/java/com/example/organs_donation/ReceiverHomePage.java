package com.example.organs_donation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class ReceiverHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receiverhomepage);

        Button heartButton = findViewById(R.id.heartBTN);
        Button liverButton = findViewById(R.id.liverBTN);
        Button lungsButton = findViewById(R.id.lungsBTN);
        Button kidneysButton = findViewById(R.id.kidneysBTN);
        Button pancreasButton = findViewById(R.id.pancreasBTN);
        Button smallBowelButton = findViewById(R.id.smallbowelBTN);

        heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceiverHomePage.this, heartList.class);
                intent.putExtra("selectedOrgan", "heartDonors");
                startActivity(intent);
            }
        });

        liverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceiverHomePage.this, LiverList.class);
                intent.putExtra("selectedOrgan", "liverDonors");
                startActivity(intent);
            }
        });

        lungsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceiverHomePage.this, lungsList.class);
                intent.putExtra("selectedOrgan", "lungDonors");
                startActivity(intent);
            }
        });

        kidneysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceiverHomePage.this, kidneyList.class);
                intent.putExtra("selectedOrgan", "kidneyDonors");
                startActivity(intent);
            }
        });

        pancreasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceiverHomePage.this, pancreasList.class);
                intent.putExtra("selectedOrgan", "pancreasDonors");
                startActivity(intent);
            }
        });

        smallBowelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass selected organ information to smallBowelList activity
                Intent intent = new Intent(ReceiverHomePage.this, smallBowelList.class);
                intent.putExtra("selectedOrgan", "small bowelDonors"); // Pass the selected organ as "smallBowel"
                startActivity(intent);
            }
        });

    }
}