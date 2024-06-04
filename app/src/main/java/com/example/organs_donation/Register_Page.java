package com.example.organs_donation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register_Page extends AppCompatActivity {

    private EditText nameEditText, emailEditText, phnumEditText, addressEditText, passwordEditText, password2EditText;
    private Button registerButton;
    private ProgressBar progressBar;
    private TextView registerTextView;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        nameEditText = findViewById(R.id.nameET);
        emailEditText = findViewById(R.id.emailidET);
        phnumEditText = findViewById(R.id.phnumET);
        addressEditText = findViewById(R.id.addressET);
        passwordEditText = findViewById(R.id.passwordET);
        password2EditText = findViewById(R.id.password2ET);
        registerButton = findViewById(R.id.registerBTN);
        progressBar = findViewById(R.id.progessBarR);  // Corrected ID here
        registerTextView = findViewById(R.id.registerTV);


        mAuth = FirebaseAuth.getInstance();

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle clicks on the register TextView if needed
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phnum = phnumEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String password2 = password2EditText.getText().toString().trim();


        // Perform input validation
        if (name.isEmpty() || email.isEmpty() || phnum.isEmpty() || address.isEmpty() || password.isEmpty() || password2.isEmpty() ) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(password2)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Register the user with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration successful
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(Register_Page.this, Login_Page.class); // Change MainActivity.class to Login_Page.class
                            startActivity(intent);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Register_Page.this, "Account Created", Toast.LENGTH_SHORT).show();
                        } else {
                            // Registration failed
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Register_Page.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
