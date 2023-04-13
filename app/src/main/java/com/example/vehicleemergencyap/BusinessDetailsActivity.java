package com.example.vehicleemergencyap;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BusinessDetailsActivity extends AppCompatActivity {

    private EditText businessNameEditText, emailEditText, phoneEditText;
    private Business business;
    private FirebaseFirestore db;
    private Button saveChangesButton, deleteButton;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(BusinessDetailsActivity.this, "No user logged in", Toast.LENGTH_SHORT).show();
            finish(); // Finish the activity and go back to the previous one
        }

        db = FirebaseFirestore.getInstance();
        businessNameEditText = findViewById(R.id.business_name_edittext);
        emailEditText = findViewById(R.id.email_edittext);
        phoneEditText = findViewById(R.id.phone_edittext);
        saveChangesButton = findViewById(R.id.save_button);
        deleteButton = findViewById(R.id.delete_button);

        Intent intent = getIntent();
        String businessId = intent.getStringExtra("businessId");

        // Retrieve the business object from Firestore using the ID
        db.collection("businesses").document(businessId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Business business = documentSnapshot.toObject(Business.class);

                            // Set the EditTexts to show the current business details
                            businessNameEditText.setText(business.getBusiness_name());
                            emailEditText.setText(business.getEmail());
                            phoneEditText.setText(business.getPhone());

                            // Save the business object for later use
                            BusinessDetailsActivity.this.business = business;
                        } else {
                            Toast.makeText(BusinessDetailsActivity.this, "No business found", Toast.LENGTH_SHORT).show();
                            finish(); // Finish the activity and go back to the previous one
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BusinessDetailsActivity.this, "Failed to retrieve business details", Toast.LENGTH_LONG).show();
                        finish(); // Finish the activity and go back to the previous one
                    }
                });
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update the business object with the new details
                business.setBusiness_name(businessNameEditText.getText().toString());
                business.setEmail(emailEditText.getText().toString());
                business.setPhone(phoneEditText.getText().toString());

                // Update the business details in the database
                if (business != null) {
                    db.collection("businesses").document(business.getId())
                            .update("business_name", business.getBusiness_name(),
                                    "email", business.getEmail(),
                                    "phone", business.getPhone())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(BusinessDetailsActivity.this, "Changes saved", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(BusinessDetailsActivity.this, "Failed to save changes", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(BusinessDetailsActivity.this, "No business selected", Toast.LENGTH_SHORT).show();
                }
            }

        });

        // Set onClickListener for the Delete button

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an AlertDialog to confirm the deletion
                AlertDialog.Builder builder = new AlertDialog.Builder(BusinessDetailsActivity.this);
                builder.setTitle("Confirm deletion");
                builder.setMessage("Are you sure you want to delete this business?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Delete the business from the database
                        db.collection("businesses").document(business.getId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(BusinessDetailsActivity.this, "Business deleted", Toast.LENGTH_SHORT).show();
                                        finish(); // Finish the activity and go back to the previous one
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(BusinessDetailsActivity.this, "Failed to delete business", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do nothing
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }
}