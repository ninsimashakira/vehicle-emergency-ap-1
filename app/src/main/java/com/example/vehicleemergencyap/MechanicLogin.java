package com.example.vehicleemergencyap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MechanicLogin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailField, passwordField;
    MaterialTextView txtSignUp;
    Button logInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_login);
        mAuth = FirebaseAuth.getInstance();

        txtSignUp = findViewById(R.id.txtSignUp);
        logInBtn = findViewById(R.id.login_button);
        emailField = findViewById(R.id.email_field);
        passwordField = findViewById(R.id.password_field);


//        Mechanic to register event
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MechanicLogin.this, MechanicRegister.class);
                startActivity(intent);
            }
        });

        //    login button

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginMechanic();
            }


            private void loginMechanic() {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();


                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    // Check if the user is a mechanic
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    db.collection("users").document(user.getUid()).get()
                                            .addOnSuccessListener(documentSnapshot -> {
                                                if (documentSnapshot.exists()) {
                                                    String role = documentSnapshot.getString("role");
                                                    if (role != null && role.equals("mechanic")) {
                                                        // Redirect to the mechanic dashboard
                                                        startActivity(new Intent(MechanicLogin.this, MechanicDashboard.class));
                                                        finish();
                                                    } else {
                                                        // User is not a mechanic
                                                        mAuth.signOut();
                                                        Toast.makeText(MechanicLogin.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    // User data not found
                                                    mAuth.signOut();
                                                    Toast.makeText(MechanicLogin.this, "User not found", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(e -> {
                                                mAuth.signOut();
                                                Toast.makeText(MechanicLogin.this, "Failed to authenticate: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            });
                                }
                            } else {
                                Toast.makeText(MechanicLogin.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }


}