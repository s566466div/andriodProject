package com.example.organs_donation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Page extends AppCompatActivity {

    private EditText userID, passwordEditText;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView textViewRegister;
    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(intent);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        userID = findViewById(R.id.userIDL);
        passwordEditText = findViewById(R.id.passwordETL);
        loginButton = findViewById(R.id.loginBTN);
        progressBar = findViewById(R.id.progessBarL);
        textViewRegister = findViewById(R.id.registerNow);

        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email = userID.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login_Page.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else {
                    // Perform login operation
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Login_Page.this, task -> {
                                if (task.isSuccessful()) {

                                    Toast.makeText(getApplicationContext(),  "Login Successful", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                                    startActivity(intent);

                                    finish();

                                } else {

                                    Toast.makeText( Login_Page.this,  "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Page.this, Register_Page.class));
                finish();
            }
        });
    }

}
